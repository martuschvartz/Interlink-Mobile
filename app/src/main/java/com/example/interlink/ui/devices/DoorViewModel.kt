package com.example.interlink.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interlink.DataSourceException
import com.example.interlink.model.Door
import com.example.interlink.model.Error
import com.example.interlink.repository.DeviceRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DoorViewModel(
    private val repository: DeviceRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(DoorUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(device: Door) {
        _uiState.update { it.copy(currentDevice = device) }
    }

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.OPEN_ACTION) },
        { state, response : Boolean -> state }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.CLOSE_ACTION) },
        { state, response : Boolean -> state }
    )

    fun lock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.LOCK_ACTION) },
        { state, response : Boolean ->
            state }
    )

    fun unlock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.UNLOCK_ACTION) },
        { state, response : Boolean ->
            state }
    )

    suspend fun <R> execAndHandle(
        func: () ->  CompletableDeferred<R?>
    ) : R? {
        val resultDeferred = func()
        return resultDeferred.await()
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (DoorUiState, R) -> DoorUiState
    ): CompletableDeferred<R?> {
        val resultDeferred = CompletableDeferred<R?>()

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            runCatching {
                block()
            }.onSuccess { response ->
                _uiState.update { updateState(it, response).copy(loading = false) }
                resultDeferred.complete(response)
            }.onFailure { e ->
                _uiState.update { it.copy(loading = false, error = handleError(e)) }
                resultDeferred.complete(null)
            }
        }

        return resultDeferred
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

}