package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice

abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType
) {
    abstract fun asRemoteModel(): RemoteDevice<*>
}