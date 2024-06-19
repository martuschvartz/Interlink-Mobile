package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteDoor
import com.example.interlink.remote.model.RemoteDoorState

class Door (
    id: String?,
    name: String,
    val status: Status,
    val lock: String
) : Device(id, name, DeviceType.DOOR) {

    override fun asRemoteModel(): RemoteDevice<RemoteDoorState> {
        val state = RemoteDoorState()
        state.status = Status.asRemoteModel(status)
        state.lock = lock

        val model = RemoteDoor()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val OPEN_ACTION = "open"
        const val CLOSE_ACTION = "close"
        const val LOCK_ACTION = "lock"
        const val UNLOCK_ACTION = "unlock"
    }
}