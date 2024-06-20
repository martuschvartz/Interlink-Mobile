package com.example.interlink.remote.model

import com.example.interlink.model.Song
import com.google.gson.annotations.SerializedName

class RemoteSpeakerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("volume")
    var volume: Int = 0

    @SerializedName("genre")
    lateinit var genre: String

    @SerializedName("song")
    lateinit var song: Song

}