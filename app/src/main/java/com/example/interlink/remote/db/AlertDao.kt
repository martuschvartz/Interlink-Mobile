package com.example.interlink.remote.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interlink.model.Alert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alert: Alert)

    @Query("SELECT * FROM alerts WHERE status = :status")
    fun getAlertsByStatus(status: String): Flow<List<Alert>>

    @Query("UPDATE alerts SET status = :newStatus WHERE id = :id")
    suspend fun updateAlertStatus(id: Int, newStatus: String)

    @Query("SELECT * FROM alerts")
    suspend fun getAllAlerts(): List<Alert>
}