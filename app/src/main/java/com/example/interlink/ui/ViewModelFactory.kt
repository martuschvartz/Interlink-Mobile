package com.example.interlink.ui;

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.interlink.ApiApplication
import com.example.interlink.repository.DeviceRepository
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.LampViewModel

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as ApiApplication)
    val deviceRepository = application.deviceRepository
    return ViewModelFactory(
        deviceRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}


public class ViewModelFactory (
    private val deviceRepository: DeviceRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(DevicesViewModel::class.java) ->
                DevicesViewModel(deviceRepository)

            isAssignableFrom(LampViewModel::class.java) ->
                LampViewModel(deviceRepository)

            isAssignableFrom(DoorViewModel::class.java) ->
                DoorViewModel(deviceRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}
