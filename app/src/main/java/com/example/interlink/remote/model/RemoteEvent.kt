package com.example.interlink.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteEvent(
    @SerializedName("id")
    val id: String,

    @SerializedName("data")
    val data: DataContent
)

data class DataContent(
    @SerializedName("data")
    val innerData: InnerData
)

data class InnerData(
    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("deviceId")
    val deviceId: String,

    @SerializedName("event")
    val event: String,

//    @SerializedName("args")
//    val args: Args
)
//
//data class Args(
//    @SerializedName("newStatus")
//    val newStatus: String
//)