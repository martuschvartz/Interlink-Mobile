package com.example.interlink.remote.model

import com.example.interlink.model.Blinds
import com.google.gson.annotations.SerializedName

class RemoteEventDeviceState {
    @SerializedName("status")
    var status: String? = null
}