package com.example.interlink.ui.devices

import com.example.interlink.model.Door
import com.example.interlink.model.Error

data class DoorUiState (
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Door? = null
)

val DoorUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
