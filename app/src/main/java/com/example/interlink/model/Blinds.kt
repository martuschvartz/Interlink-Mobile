package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteBlinds
import com.example.interlink.remote.model.RemoteBlindsState

class Blinds (
    id: String?,
    name: String,
    val status: Status,
    val level: Int,
    val currentLevel: Int,
) : Device(id, name, DeviceType.BLINDS) {

    override fun asRemoteModel(): RemoteDevice<RemoteBlindsState> {
        val state = RemoteBlindsState()
        state.status = Status.asRemoteModel(status)
        state.level = level
        state.currentLevel = currentLevel

        val model = RemoteBlinds()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val OPEN_ACTION  = "open"
        const val CLOSE_ACTION = "close"
        const val SET_LEVEL_ACTION = "setLevel"
    }
}