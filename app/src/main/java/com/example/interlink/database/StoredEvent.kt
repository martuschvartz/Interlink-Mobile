package com.example.interlink.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.interlink.remote.model.RemoteEvent

@Entity(tableName = "events", primaryKeys = ["timestamp", "name", "description"])
    data class StoredEvent(
        @ColumnInfo(name = "name")
        val name: String = "",

        @ColumnInfo(name = "description")
        val description: String = "",

        @ColumnInfo(name = "timestamp")
        val timestamp: String = ""
    )
