package com.example.interlink.model

import com.example.interlink.remote.model.RemoteEvent
import com.google.gson.JsonObject

abstract class Event(
    val timestamp: String?,
    val deviceInt: String?,
    val event: String?,
    val args: JsonObject?,
) {
    abstract fun asRemoteModel(): RemoteEvent
}