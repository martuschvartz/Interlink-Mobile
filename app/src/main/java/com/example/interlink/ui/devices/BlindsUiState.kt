package com.example.interlink.ui.devices

import com.example.interlink.model.Blinds
import com.example.interlink.model.Error

data class BlindsUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Blinds? = null
)

val BlindsUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
