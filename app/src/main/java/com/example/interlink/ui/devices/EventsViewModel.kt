package com.example.interlink.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.DataSourceException
import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.repository.DeviceRepository
import com.example.interlink.model.Error
import com.example.interlink.model.Event
import com.example.interlink.remote.model.RemoteEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel(
    private val repository: DeviceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            collectOnViewModelScope(
                repository.events
            ) { state, response ->
                var filtered: List<RemoteEvent> = filterEvents(response)
                state.copy(events = filtered + state.events) }
        }

    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: suspend (EventsUiState, T) -> EventsUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

    private suspend fun filterEvents(eventList : List<RemoteEvent>) : List<RemoteEvent> {
        var dev : Device
        val toRet = eventList.filter{ event ->
            dev = repository.getDevice(event.deviceId)
            event.device = dev
            when(dev.type){
                DeviceType.DOOR -> {
                    when (event.event) {
                        "statusChanged" -> true
                        "lockChanged" -> true
                        else -> false
                    }
                }
                DeviceType.ALARM -> event.event == "statusChanged"
                DeviceType.BLINDS-> event.event == "statusChanged"
                else -> false
            }
        }
        return toRet
    }
}