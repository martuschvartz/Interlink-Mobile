package com.example.interlink.remote.model

import com.google.gson.annotations.SerializedName

class RemoteDeviceType {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("powerUsage")
    var powerUsage: Int? = null

    companion object {
        const val LAMP_DEVICE_TYPE_ID = "go46xmbqeomjrsjr"
        const val AC_DEVICE_TYPE_ID = "li6cbv5sdlatti0j"
        const val BLINDS_DEVICE_TYPE_ID = "eu0v2xgprrhhg41g"
        const val SPEAKER_DEVICE_TYPE_ID = "c89b94e8581855bc"
        const val DOOR_DEVICE_TYPE_ID = "lsf78ly0eqrjbz91"
    }
}