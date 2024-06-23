package com.example.interlink.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.R
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Blinds
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp
import com.example.interlink.model.Speaker
import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.devices.SpeakerViewModel
import com.example.interlink.ui.getViewModelFactory

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    lampViewModel: LampViewModel = viewModel(factory = getViewModelFactory()),
    doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
    acViewModel: AcViewModel = viewModel(factory = getViewModelFactory()),
    alarmViewModel: AlarmViewModel = viewModel(factory = getViewModelFactory()),
    blindsViewModel: BlindsViewModel = viewModel(factory = getViewModelFactory()),
    speakerViewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
    favDevViewModel : FavoritesEntryViewModel,
    useLazyColumn : Boolean,
) {

    Log.d("DEBUG", "en devicePage me llega uselazycolumn como $useLazyColumn")
    val uiState by viewModel.uiState.collectAsState()
    var selectedDeviceId by remember { mutableStateOf<String?>(null) }
    var expandedDeviceId by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = stringResource(id = R.string.devices),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.Top
        ) {
            if (useLazyColumn) {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.devices) { device ->
                        Box(modifier = Modifier.padding(10.dp)) {
                            val deviceViewModel = when (device.type) {
                                DeviceType.LAMP -> lampViewModel
                                DeviceType.SPEAKER -> speakerViewModel
                                DeviceType.BLINDS -> blindsViewModel
                                DeviceType.ALARM -> alarmViewModel
                                DeviceType.DOOR -> doorViewModel
                                DeviceType.AC -> acViewModel
                            }
                            DeviceCard(
                                currentDevice = device.id == selectedDeviceId,
                                device = device,
                                viewModel = deviceViewModel,
                                expanded = device.id == expandedDeviceId,
                                favDevViewModel = favDevViewModel,
                                landscape = !useLazyColumn
                            ) { device ->
                                expandedDeviceId =
                                    if (device.id == expandedDeviceId) null else device.id
                                selectedDeviceId = device.id
                                when (device.type) {
                                    DeviceType.LAMP -> lampViewModel.setCurrentDevice(device as Lamp)
                                    DeviceType.DOOR -> doorViewModel.setCurrentDevice(device as Door)
                                    DeviceType.BLINDS -> blindsViewModel.setCurrentDevice(device as Blinds)
                                    DeviceType.ALARM -> alarmViewModel.setCurrentDevice(device as Alarm)
                                    DeviceType.AC -> acViewModel.setCurrentDevice(device as Ac)
                                    DeviceType.SPEAKER -> speakerViewModel.setCurrentDevice(device as Speaker)
                                }
                            }
                        }
                    }
                }
            } else {
                LazyRow(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    items(uiState.devices) { device ->
                        Box(modifier = Modifier.padding(10.dp)) {
                            val deviceViewModel = when (device.type) {
                                DeviceType.LAMP -> lampViewModel
                                DeviceType.SPEAKER -> speakerViewModel
                                DeviceType.BLINDS -> blindsViewModel
                                DeviceType.ALARM -> alarmViewModel
                                DeviceType.DOOR -> doorViewModel
                                DeviceType.AC -> acViewModel
                            }

                            DeviceCard(
                                currentDevice = device.id == selectedDeviceId,
                                device = device,
                                viewModel = deviceViewModel,
                                expanded = device.id == expandedDeviceId,
                                favDevViewModel = favDevViewModel,
                                landscape = !useLazyColumn
                            ) { device ->
                                expandedDeviceId =
                                    if (device.id == expandedDeviceId) null else device.id
                                selectedDeviceId = device.id
                                when (device.type) {
                                    DeviceType.LAMP -> lampViewModel.setCurrentDevice(device as Lamp)
                                    DeviceType.DOOR -> doorViewModel.setCurrentDevice(device as Door)
                                    DeviceType.BLINDS -> blindsViewModel.setCurrentDevice(device as Blinds)
                                    DeviceType.ALARM -> alarmViewModel.setCurrentDevice(device as Alarm)
                                    DeviceType.AC -> acViewModel.setCurrentDevice(device as Ac)
                                    DeviceType.SPEAKER -> speakerViewModel.setCurrentDevice(device as Speaker)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
