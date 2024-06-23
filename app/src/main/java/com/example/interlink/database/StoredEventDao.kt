package com.example.interlink.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interlink.remote.model.RemoteEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: StoredEvent)

    @Delete
    suspend fun delete(event: StoredEvent)

    @Query("SELECT name, description, timestamp FROM events ORDER BY timestamp DESC")
    fun getStoredEvents(): Flow<List<StoredEventData>>

    @Query("SELECT name, description, timestamp FROM events ORDER BY timestamp DESC LIMIT 3")
    fun getRecentEvents(): Flow<List<StoredEventData>>
    
    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}