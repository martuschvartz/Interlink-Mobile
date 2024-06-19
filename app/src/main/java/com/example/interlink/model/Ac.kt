package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteAc
import com.example.interlink.remote.model.RemoteAcState
/*
"status": "off",
        "temperature": 24,
        "mode": "cool",
        "verticalSwing": "auto",
        "horizontalSwing": "auto",
        "fanSpeed": "auto"

 */
class Ac (
    id: String?,
    name: String,
    val status: Status,
    val temperature: Int,
    val mode: String,
    val verticalSwing: String,
    val horizontalSwing: String,
    val fanSpeed: String,
) : Device(id, name, DeviceType.AC) {

    override fun asRemoteModel(): RemoteDevice<RemoteAcState> {
        val state = RemoteAcState()
        state.status = Status.asRemoteModel(status)
        state.temperature = temperature
        state.mode = mode
        state.verticalSwing = verticalSwing
        state.horizontalSwing = horizontalSwing
        state.fanSpeed = fanSpeed

        val model = RemoteAc()
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