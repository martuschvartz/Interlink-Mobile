package com.example.interlink

import android.app.Application
import com.example.interlink.remote.DeviceRemoteDataSource
import com.example.interlink.remote.api.RetrofitClient
import com.example.interlink.repository.DeviceRepository

class ApiApplication : Application() {

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)
}