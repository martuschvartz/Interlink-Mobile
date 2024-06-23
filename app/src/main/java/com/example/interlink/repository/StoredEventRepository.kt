package com.example.interlink.repository

import com.example.interlink.database.StoredEvent
import com.example.interlink.database.StoredEventDao
import com.example.interlink.database.StoredEventData
import kotlinx.coroutines.flow.Flow

class StoredEventRepository(private val storedEventDao: StoredEventDao) {

    fun getStoredEvents(): Flow<List<StoredEventData>> {
        return storedEventDao.getStoredEvents()
    }

    suspend fun insertEvent(event: StoredEvent) {
        storedEventDao.insert(event)
    }

    suspend fun deleteEvent(event: StoredEvent) {
        storedEventDao.delete(event)
    }
}