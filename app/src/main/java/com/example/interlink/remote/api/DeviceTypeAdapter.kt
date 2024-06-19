package com.example.interlink.remote.api

import com.example.interlink.remote.model.RemoteAc
import com.example.interlink.remote.model.RemoteAlarm
import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteDeviceType
import com.example.interlink.remote.model.RemoteDoor
import com.example.interlink.remote.model.RemoteLamp
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DeviceTypeAdapter : JsonDeserializer<RemoteDevice<*>?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RemoteDevice<*>? {
        val gson = Gson()
        val jsonDeviceObject = json.asJsonObject
        val jsonDeviceTypeObject = jsonDeviceObject["type"].asJsonObject
        val deviceTypeId = jsonDeviceTypeObject["id"].asString

        return when(deviceTypeId) {
            RemoteDeviceType.LAMP_DEVICE_TYPE_ID     -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteLamp?>() {}.type)
            RemoteDeviceType.DOOR_DEVICE_TYPE_ID     -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteDoor?>() {}.type)
            RemoteDeviceType.AC_DEVICE_TYPE_ID       -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteAc?>() {}.type)
            RemoteDeviceType.ALARM_DEVICE_TYPE_ID    -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteAlarm?>() {}.type)

            else -> null
        }
    }
}