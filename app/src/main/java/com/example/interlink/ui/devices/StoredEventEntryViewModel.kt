package com.example.interlink.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.interlink.database.StoredEvent
import com.example.interlink.database.StoredEventDao
import com.example.interlink.database.StoredEventData
import com.example.interlink.repository.StoredEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class StoredEventEntryViewModel(private val repository: StoredEventRepository) : ViewModel(){

    suspend fun insertEvent(data : StoredEventData){
        viewModelScope.launch {
            repository.insertEvent(StoredEvent(name = data.name, timestamp = data.timestamp, description = data.description))
        }
        getStoredEvents()
    }

    suspend fun deleteEvent(data : StoredEventData){
        viewModelScope.launch {
            repository.deleteEvent(StoredEvent(name = data.name, timestamp = data.timestamp, description = data.description))
        }
        getStoredEvents()
    }

    fun getStoredEvents() : Flow<List<StoredEventData>>{
        return repository.getStoredEvents()
    }

    fun deleteAllEvents(){
        viewModelScope.launch {
            repository.deleteAllEvents()
        }
    }

    fun getRecentEvents() : Flow<List<StoredEventData>>{
        return repository.getRecentEvents()
    }


}

class StoredEventEntryViewModelFactory(private val storedEventDao: StoredEventDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoredEventEntryViewModel::class.java)) {
            val repository = StoredEventRepository(storedEventDao)
            @Suppress("UNCHECKED_CAST")
            return StoredEventEntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
