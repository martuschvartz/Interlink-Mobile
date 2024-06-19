package com.example.interlink.remote.model

import com.example.interlink.model.Door

class RemoteDoor : RemoteDevice<RemoteDoorState>() {
    override fun asModel(): Door {
        return Door(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            lock = state.lock
        )
    }
}