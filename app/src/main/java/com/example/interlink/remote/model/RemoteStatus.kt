package com.example.interlink.remote.model

import com.example.interlink.model.Status

object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"

    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            else -> Status.OFF
        }
    }
}