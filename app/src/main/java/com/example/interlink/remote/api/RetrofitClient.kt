package com.example.interlink.remote.api

import com.example.interlink.BuildConfig
import com.example.interlink.remote.model.RemoteDevice
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val gson = GsonBuilder()
    .registerTypeAdapter(Date::class.java, DateTypeAdapter())
    .registerTypeAdapter(com.example.interlink.remote.model.RemoteDevice::class.java, DeviceTypeAdapter())
    .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .addConverterFactory(PreprocessingConverterFactory(gson))
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

object RetrofitClient {
    val deviceService : DeviceService by lazy {
        retrofit.create(DeviceService::class.java)
    }
}