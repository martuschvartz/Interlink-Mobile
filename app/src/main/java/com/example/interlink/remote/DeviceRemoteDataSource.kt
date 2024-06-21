package com.example.interlink.remote

import com.example.interlink.remote.api.DeviceService
import com.example.interlink.remote.model.DataContent
import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceRemoteDataSource (private val deviceService: DeviceService
) : RemoteDataSource() {

    val devices: Flow<List<RemoteDevice<*>>> = flow {
        while (true) {
            val devices = handleApiResponse {
                deviceService.getDevices()
            }
            emit(devices)
            delay(DELAY)
        }
    }

    suspend fun getDevice(deviceId: String): RemoteDevice<*> {
        return handleApiResponse {
            deviceService.getDevice(deviceId)
        }
    }

    suspend fun addDevice(device: RemoteDevice<*>): RemoteDevice<*> {
        return handleApiResponse {
            deviceService.addDevice(device)
        }
    }

    suspend fun modifyDevice(device: RemoteDevice<*>): Boolean {
        return handleApiResponse {
            deviceService.modifyDevice(device.id!!, device)
        }
    }

    suspend fun deleteDevice(deviceId: String): Boolean {
        return handleApiResponse {
            deviceService.deleteDevice(deviceId)
        }
    }

    suspend fun executeDeviceAction(
        deviceId: String,
        action: String,
        parameters: Array<*>
    ): Array<*> {
        return handleApiResponse {
            deviceService.executeDeviceAction(deviceId, action, parameters)
        }
    }

    suspend fun getEvents(): DataContent {
        return handleApiEvent {
            deviceService.getEvents()
        }
    }

    companion object {
        const val DELAY: Long = 10000
    }
}