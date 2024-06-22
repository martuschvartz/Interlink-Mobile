package com.example.interlink.ui.devices

import com.example.interlink.model.Speaker
import com.example.interlink.model.Error

data class SpeakerUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Speaker? = null,
)

val SpeakerUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
