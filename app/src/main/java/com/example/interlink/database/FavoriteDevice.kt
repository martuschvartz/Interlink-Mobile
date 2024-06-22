package com.example.interlink.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
    data class FavoriteDevice(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String = ""
    )
