package com.example.interlink.remote.model

import com.example.interlink.model.Event
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class RemoteEvent {

    @SerializedName("timestamp")
    lateinit var timestamp: String

    @SerializedName("deviceId")
    lateinit var deviceId: String

    @SerializedName("event")
    lateinit var event: String

    @SerializedName("args")
    lateinit var args: JsonObject

}