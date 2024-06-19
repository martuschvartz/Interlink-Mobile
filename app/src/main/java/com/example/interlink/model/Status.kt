package com.example.interlink.model

import com.example.interlink.remote.model.RemoteStatus

enum class Status {
    ON, OFF;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                else -> RemoteStatus.OFF
            }
        }
    }
}