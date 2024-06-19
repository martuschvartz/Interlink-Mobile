package com.example.interlink.ui.devices

import com.example.interlink.model.Alarm
import com.example.interlink.model.Error

data class AlarmUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Alarm? = null
)

val AlarmUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
