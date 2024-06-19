package com.example.interlink.remote.model

import com.example.interlink.model.Status

object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"
    const val OPENED = "opened"
    const val CLOSED = "closed"


    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            OFF -> Status.OFF
            OPENED -> Status.OPENED
            CLOSED -> Status.CLOSED
            else -> Status.OFF
        }
    }
}