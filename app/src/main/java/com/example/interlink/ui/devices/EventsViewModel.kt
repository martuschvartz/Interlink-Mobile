package com.example.interlink.ui.devices

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.DataSourceException
import com.example.interlink.R
import com.example.interlink.database.FavoritesDatabase
import com.example.interlink.database.StoredEvent
import com.example.interlink.database.StoredEventData
import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.repository.DeviceRepository
import com.example.interlink.model.Error
import com.example.interlink.model.Event
import com.example.interlink.remote.model.RemoteEvent
import com.example.interlink.repository.StoredEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class EventsViewModel(
    private val repository: DeviceRepository,
    private val eventRepo: StoredEventRepository,
    context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            collectOnViewModelScope(
                repository.events
            ) { state, response ->
                var filtered: List<RemoteEvent> = filterEvents(response)
                filtered.forEach{ event ->
                    Log.d("DEBUG", "Inertamos: ${StoredEvent(name = event.device.name, description =eventDescription(event = event, context = context), timestamp = event.timestamp)}")
                    eventRepo.insertEvent(event = StoredEvent(name = event.device.name, description = eventDescription(event = event, context = context), timestamp = event.timestamp))
                }
                state.copy(events = filtered + state.events) }
        }

    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: suspend (EventsUiState, T) -> EventsUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

    private suspend fun filterEvents(eventList : List<RemoteEvent>) : List<RemoteEvent> {
        var dev : Device
        val toRet = eventList.filter{ event ->
            dev = repository.getDevice(event.deviceId)
            event.device = dev
            when(dev.type){
                DeviceType.DOOR -> {
                    when (event.event) {
                        "statusChanged" -> true
                        "lockChanged" -> true
                        else -> false
                    }
                }
                DeviceType.ALARM -> event.event == "statusChanged"
                DeviceType.BLINDS-> event.event == "statusChanged"
                else -> false
            }
        }
        return toRet
    }

    private fun eventDescription(event: RemoteEvent, context: Context?) : String{
        var toRet : String = ""
        val argsObject = event.args.asJsonObject
        when(event.device.type){
            DeviceType.DOOR -> {
                when (event.event) {
                    "statusChanged" -> {
                        when(argsObject["newStatus"].asString){
                            "opened" -> toRet = context!!.getString(R.string.openedDoor)
                            "closed" -> toRet = context!!.getString(R.string.closedDoor)
                        }
                    }
                    "lockChanged" -> when(argsObject["newLock"].asString){
                        "locked" -> toRet = context!!.getString(R.string.lockedDoor)
                        "unlocked" -> toRet = context!!.getString(R.string.unlockedDoor)
                    }
                }
            }
            DeviceType.ALARM -> when(argsObject["newStatus"].asString){
                "armedStay" -> toRet= context!!.getString(R.string.armedAlarm)
                "armedAway" -> toRet = context!!.getString(R.string.armedAlarm)
                "disarmed" -> toRet = context!!.getString(R.string.disarmedAlarm)
            }
            DeviceType.BLINDS-> when(argsObject["newStatus"].asString){
                "opened" -> toRet = context!!.getString(R.string.openedBlinds)
                "closed" -> toRet = context!!.getString(R.string.closedBlinds)
                "opening" -> toRet = context!!.getString(R.string.openingBlinds)
                "closing" -> toRet = context!!.getString(R.string.closingBlinds)
            }

            else -> {}
        }

        return toRet
    }

    private fun parseTimestamp(timestamp: String): Instant? {
        return try {
            Instant.parse(timestamp)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun formatTimestamp(instant: Instant, pattern: String = "HH:mm"): String {
        val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }
}