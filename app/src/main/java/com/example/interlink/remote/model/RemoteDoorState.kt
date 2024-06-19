package com.example.interlink.remote.model

import com.google.gson.annotations.SerializedName

class RemoteDoorState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("lock")
    lateinit var lock: String

}