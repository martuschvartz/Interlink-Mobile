package com.example.interlink.ui.devices

import com.example.interlink.model.Error
import com.example.interlink.model.Event
import com.example.interlink.remote.model.RemoteEvent

data class EventsUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val newEvents: Boolean = false,
    val events: List<RemoteEvent> = emptyList()
)
