package com.example.interlink.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.interlink.database.StoredEvent
import com.example.interlink.database.StoredEventDao
import com.example.interlink.database.StoredEventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class StoredEventEntryViewModel(private val strDevDao: StoredEventDao) : ViewModel(){

    suspend fun insertEvent(data : StoredEventData){
        viewModelScope.launch {
            strDevDao.insert(StoredEvent(name = data.name, timestamp = data.timestamp, description = data.description))
        }
        getStoredEvents()
    }

    suspend fun deleteEvent(data : StoredEventData){
        viewModelScope.launch {
            strDevDao.delete(StoredEvent(name = data.name, timestamp = data.timestamp, description = data.description))
        }
        getStoredEvents()
    }

    fun getStoredEvents() : Flow<List<StoredEventData>>{
//        Log.d("DEBUG", "${favDevDao.getFavoritesId()}")
        return strDevDao.getStoredEvents()
    }

}

class StoredEventEntryViewModelFactory(private val strDevDao: StoredEventDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(StoredEventEntryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return StoredEventEntryViewModel(strDevDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

