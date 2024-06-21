package com.example.interlink.model

import com.example.interlink.remote.model.RemoteStatus

enum class Status {
    ON, OFF, OPENED, CLOSED, OPENING, CLOSING, PLAYING, STOPPED, PAUSED;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                OFF -> RemoteStatus.OFF
                OPENED -> RemoteStatus.OPENED
                CLOSED -> RemoteStatus.CLOSED
                OPENING -> RemoteStatus.OPENING
                CLOSING -> RemoteStatus.CLOSING
                PLAYING -> RemoteStatus.PLAYING
                STOPPED -> RemoteStatus.STOPPED
                PAUSED -> RemoteStatus.PAUSED
                else -> RemoteStatus.OFF
            }
        }
    }
}