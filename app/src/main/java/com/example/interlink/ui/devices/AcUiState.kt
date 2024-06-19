package com.example.interlink.ui.devices

import com.example.interlink.model.Ac
import com.example.interlink.model.Error

data class AcUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Ac? = null
)

val AcUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
