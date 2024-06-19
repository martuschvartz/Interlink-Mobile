package com.example.interlink.ui.devices

import com.example.interlink.model.Lamp
import com.example.interlink.model.Error

data class LampUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Lamp? = null
)

val LampUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
