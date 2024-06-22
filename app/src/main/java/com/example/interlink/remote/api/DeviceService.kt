package com.example.interlink.remote.api

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteEvent
import com.example.interlink.remote.model.RemoteResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeviceService {
    @GET("devices")
    suspend fun getDevices(): Response<RemoteResult<List<RemoteDevice<*>>>>

    @POST("devices")
    suspend fun addDevice(@Body device: RemoteDevice<*>): Response<RemoteResult<RemoteDevice<*>>>

    @GET("devices/{deviceId}")
    suspend fun getDevice(@Path("deviceId") deviceId: String): Response<RemoteResult<RemoteDevice<*>>>

    @PUT("devices/{deviceId}")
    suspend fun modifyDevice(
        @Path("deviceId") deviceId: String,
        @Body device: RemoteDevice<*>
    ): Response<RemoteResult<Boolean>>

    @DELETE("devices/{deviceId}")
    suspend fun deleteDevice(@Path("deviceId") deviceId: String): Response<RemoteResult<Boolean>>

    @PUT("devices/{deviceId}/{action}")
    suspend fun <T : Any> executeDeviceAction(
        @Path("deviceId") deviceId: String,
        @Path("action") action: String,
        @Body parameters: Array<*>
    ): Response<RemoteResult<T>>

    @GET("devices/events")
    suspend fun getEvents(): Response<List<RemoteEvent>>
}