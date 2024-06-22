package com.example.interlink.remote.model

import com.example.interlink.model.DeviceType
import com.example.interlink.model.EventDevice

class RemoteEventDevice : RemoteDevice<RemoteEventDeviceState>() {
    override fun asModel(): EventDevice {
        return EventDevice(
            id = id,
            name = name,
            // dummy
            type = DeviceType.LAMP
        )
    }
}