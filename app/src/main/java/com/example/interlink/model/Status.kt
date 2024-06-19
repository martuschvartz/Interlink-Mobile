package com.example.interlink.model

import com.example.interlink.remote.model.RemoteStatus

enum class Status {
    ON, OFF, OPENED, CLOSED;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                OFF -> RemoteStatus.OFF
                OPENED -> RemoteStatus.OPENED
                CLOSED -> RemoteStatus.CLOSED
                else -> RemoteStatus.OFF
            }
        }
    }
}