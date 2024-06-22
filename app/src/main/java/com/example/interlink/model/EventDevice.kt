package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteEventDevice
import com.example.interlink.remote.model.RemoteEventDeviceState

class EventDevice(
    id: String?,
    name: String,
    type: DeviceType
) : Device(id, name, type) {

    override fun asRemoteModel(): RemoteDevice<RemoteEventDeviceState> {
        val state = null

        val model = RemoteEventDevice()
        model.id = id
        model.name = name
        return model
    }

}