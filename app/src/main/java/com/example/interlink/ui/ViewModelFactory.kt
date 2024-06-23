package com.example.interlink.ui;

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.interlink.ApiApplication
import com.example.interlink.model.Event
import com.example.interlink.repository.DeviceRepository
import com.example.interlink.repository.StoredEventRepository
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.EventsViewModel
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.devices.SpeakerViewModel

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as ApiApplication)
    val deviceRepository = application.deviceRepository
    val eventRepository = application.eventRepository
    return ViewModelFactory(
        deviceRepository,
        eventRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs,
        application
    )
}


public class ViewModelFactory (
    private val deviceRepository: DeviceRepository,
    private val eventRepository: StoredEventRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null,
    private val context: Context
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(DevicesViewModel::class.java) ->
                DevicesViewModel(deviceRepository)

            isAssignableFrom(EventsViewModel::class.java) ->
                EventsViewModel(deviceRepository, eventRepository, context)

            isAssignableFrom(LampViewModel::class.java) ->
                LampViewModel(deviceRepository)

            isAssignableFrom(DoorViewModel::class.java) ->
                DoorViewModel(deviceRepository)

            isAssignableFrom(AcViewModel::class.java) ->
                AcViewModel(deviceRepository)

            isAssignableFrom(AlarmViewModel::class.java) ->
                AlarmViewModel(deviceRepository)

            isAssignableFrom(BlindsViewModel::class.java) ->
                BlindsViewModel(deviceRepository)

            isAssignableFrom(SpeakerViewModel::class.java) ->
                SpeakerViewModel(deviceRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}
