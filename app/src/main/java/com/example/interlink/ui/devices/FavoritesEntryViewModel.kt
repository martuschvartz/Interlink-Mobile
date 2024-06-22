package com.example.interlink.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.interlink.database.FavoriteDevice
import com.example.interlink.database.FavoriteDeviceDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class FavoritesEntryViewModel(private val favDevDao: FavoriteDeviceDao) : ViewModel(){

    // inserta un favorito (o sea su id)
    suspend fun insertFavorite(id: String){
        val favDev = FavoriteDevice(id)
        viewModelScope.launch {
            favDevDao.insert(favDev)
        }
        getFavoritesId()
    }

    // elimina un favorito
    suspend fun deleteFavorite(id: String){
        val favDev = FavoriteDevice(id)
        viewModelScope.launch {
            favDevDao.delete(favDev)
        }
        getFavoritesId()
    }

    // me da una lista de los favs
    fun getFavoritesId() : Flow<List<String>>{
        return favDevDao.getFavoritesId()
    }
}

class FavoritesEntryViewModelFactory(private val favDevDao: FavoriteDeviceDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FavoritesEntryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavoritesEntryViewModel(favDevDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

