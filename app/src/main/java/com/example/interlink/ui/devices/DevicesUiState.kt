package com.example.interlink.ui.devices

import com.example.interlink.model.Device
import com.example.interlink.model.Error
import com.example.interlink.remote.model.DataContent

data class DevicesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val devices: List<Device> = emptyList(),
    val newEvents: Boolean = false,
    val events: List<DataContent> = emptyList()
)
