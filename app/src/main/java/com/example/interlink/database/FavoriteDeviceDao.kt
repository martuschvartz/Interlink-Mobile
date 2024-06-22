package com.example.interlink.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDeviceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favDevice : FavoriteDevice)

    @Delete
    suspend fun delete(favDevice : FavoriteDevice)

    @Query("SELECT id FROM favorites")
    fun getFavoritesId(): Flow<List<String>>

}