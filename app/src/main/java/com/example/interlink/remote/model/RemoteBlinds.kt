package com.example.interlink.remote.model

import com.example.interlink.model.Blinds

class RemoteBlinds : RemoteDevice<RemoteBlindsState>() {
    override fun asModel(): Blinds {
        return Blinds(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            level = state.level,
            currentLevel = state.currentLevel
        )
    }
}