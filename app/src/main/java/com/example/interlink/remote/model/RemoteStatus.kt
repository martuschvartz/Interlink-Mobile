package com.example.interlink.remote.model

import com.example.interlink.model.Status
object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"
    const val OPENED = "opened"
    const val CLOSED = "closed"
    const val OPENING = "opening"
    const val CLOSING = "closing"
    const val PLAYING = "playing"
    const val STOPPED = "stopped"
    const val PAUSED = "paused"
    const val ARMEDSTAY = "armedStay"
    const val ARMEDAWAY = "armedAway"
    const val DISARMED = "disarmed"


    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            OFF -> Status.OFF
            OPENED -> Status.OPENED
            CLOSED -> Status.CLOSED
            OPENING -> Status.OPENING
            CLOSING -> Status.CLOSING
            PLAYING -> Status.PLAYING
            STOPPED -> Status.STOPPED
            PAUSED -> Status.PAUSED
            ARMEDSTAY -> Status.ARMEDSTAY
            ARMEDAWAY -> Status.ARMEDAWAY
            DISARMED -> Status.DISARMED
            else -> Status.OFF
        }
    }
}