package com.example.interlink.repository

import com.example.interlink.model.Device
import com.example.interlink.remote.DeviceRemoteDataSource
import com.example.interlink.remote.model.RemoteEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeviceRepository(
    private val remoteDataSource: DeviceRemoteDataSource
)  {
    val devices: Flow<List<Device>> =
        remoteDataSource.devices
            .map { it.map { jt -> jt.asModel() } }

    val events: Flow<List<RemoteEvent>> =
        remoteDataSource.events
//            .map{ it.map { jt -> jt.asModel()}}


    suspend fun getDevice(deviceId: String): Device {
        return remoteDataSource.getDevice(deviceId).asModel()
    }

    suspend fun addDevice(device: Device): Device {
        return remoteDataSource.addDevice(device.asRemoteModel()).asModel()
    }

    suspend fun modifyDevice(device: Device): Boolean {
        return remoteDataSource.modifyDevice(device.asRemoteModel())
    }

    suspend fun deleteDevice(deviceId: String): Boolean {
        return remoteDataSource.deleteDevice(deviceId)
    }

    suspend fun executeDeviceAction(
        deviceId: String,
        action: String,
        parameters: Array<*> = emptyArray<Any>()
    ): Array<*> {
        return remoteDataSource.executeDeviceAction(deviceId, action, parameters)
    }

//    suspend fun getEvents(): RemoteEvent {
//        return remoteDataSource.getEvents()
//    }
}