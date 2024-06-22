package com.example.interlink.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interlink.DataSourceException
import com.example.interlink.model.Alarm
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

class AlarmViewModel(
    private val repository: DeviceRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(device: Alarm) {
        _uiState.update { it.copy(currentDevice = device) }
    }

    fun armAway(pin: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Alarm.ARM_AWAY_ACTION, arrayOf(pin)) },
        { state, response : Boolean -> state }
    )

    fun armStay(pin: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Alarm.ARM_STAY_ACTION, arrayOf(pin)) },
        { state, response : Boolean -> state }
    )

    fun disarm(pin: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Alarm.DISARM_ACTION, arrayOf(pin)) },
        { state, response : Boolean -> state }
    )

    fun changeSecurityCode(oldPin: String, newPin: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Alarm.CHANGE_SECURITY_CODE_ACTION, arrayOf(oldPin, newPin)) },
        { state, response : Boolean -> state }
    )

    suspend fun <Boolean> fetchNewVal(
        func: () -> CompletableDeferred<Boolean>
    ) : Boolean{
        val resultDeferred = func()
        return resultDeferred.await()
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (AlarmUiState, T) -> AlarmUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (AlarmUiState, R) -> AlarmUiState
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