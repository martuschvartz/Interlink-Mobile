package com.example.interlink.ui.alerts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.interlink.model.Alert
import com.example.interlink.remote.db.AlertDao
import kotlinx.coroutines.launch

class AlertViewModel(private val alertDao: AlertDao) : ViewModel() {

    fun insertAlert(title: String, message: String, timestamp: Long, status: String) {
        val alert = Alert(title = title, message = message, timestamp = timestamp, status = status)
        viewModelScope.launch {
            alertDao.insert(alert)
            logAlerts()
        }
    }

    private suspend fun logAlerts() {
        val currentAlerts = alertDao.getAllAlerts()
        Log.d("AlertViewModel", "Current alerts in database: $currentAlerts")
    }

    fun getAlertsByStatus(status: String): LiveData<List<Alert>> {
        return alertDao.getAlertsByStatus(status).asLiveData()
    }
}


class AlertViewModelFactory(private val alertDao: AlertDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlertViewModel(alertDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

