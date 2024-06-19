package com.example.interlink.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interlink.DataSourceException
import com.example.interlink.model.Ac
import com.example.interlink.model.Error
import com.example.interlink.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AcViewModel(
    private val repository: DeviceRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(AcUiState())
    val uiState = _uiState.asStateFlow()

    // Esta es la funcion que determina q device es el q vas a estar controlando con el ViewModel
    fun setCurrentDevice(device: Ac) {
        _uiState.update { it.copy(currentDevice = device) }
    }

    // Aca van las funciones en si (el Ac.OPEN_ACTION esta en la clase Ac mas arriba)
    fun turnOn() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.TURN_ON_ACTION) },
        { state, _ -> state }
    )

    fun turnOff() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.TURN_OFF_ACTION) },
        { state, _ -> state }
    )

    fun setTemperature(temperature: Int) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_TEMPERATURE_ACTION, arrayOf(temperature)) },
        { state, _ -> state }
    )

    fun setMode(mode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_MODE_ACTION, arrayOf(mode)) },
        { state, _ -> state }
    )

    fun setVerticalSwing(mode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_VERTICAL_SWING_ACTION, arrayOf(mode)) },
        { state, _ -> state }
    )

    fun setHorizontalSwing(mode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_HORIZONTAL_SWING_ACTION, arrayOf(mode)) },
        { state, _ -> state }
    )

    fun setFanSpeed(mode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_FAN_SPEED_ACTION, arrayOf(mode)) },
        { state, _ -> state }
    )

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (AcUiState, T) -> AcUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (AcUiState, R) -> AcUiState
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { updateState(it, response).copy(loading = false) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

}