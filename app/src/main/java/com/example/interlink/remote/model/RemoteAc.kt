package com.example.interlink.remote.model

import com.example.interlink.model.Ac

class RemoteAc : RemoteDevice<RemoteAcState>() {
    override fun asModel(): Ac {
        return Ac(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            temperature = state.temperature,
            mode = state.mode,
            verticalSwing = state.verticalSwing,
            horizontalSwing = state.horizontalSwing,
            fanSpeed = state.fanSpeed
        )
    }
}

/*
"status": "off",
        "temperature": 24,
        "mode": "cool",
        "verticalSwing": "auto",
        "horizontalSwing": "auto",
        "fanSpeed": "auto"
 */