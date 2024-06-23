package com.example.interlink.ui.pages


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.ui.components.EventCard
import com.example.interlink.ui.devices.StoredEventEntryViewModel
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Blinds
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp
import com.example.interlink.model.Speaker
import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.devices.SpeakerViewModel
import com.example.interlink.ui.getViewModelFactory
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    favDevViewModel : FavoritesEntryViewModel,
    storedEvents : StoredEventEntryViewModel,
    devicesViewModel : DevicesViewModel = viewModel(factory = getViewModelFactory()),
    lampViewModel: LampViewModel = viewModel(factory = getViewModelFactory()),
    doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
    acViewModel: AcViewModel = viewModel(factory = getViewModelFactory()),
    alarmViewModel: AlarmViewModel = viewModel(factory = getViewModelFactory()),
    blindsViewModel: BlindsViewModel = viewModel(factory = getViewModelFactory()),
    speakerViewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
) {
    val recents by storedEvents.getRecentEvents().collectAsState(initial = emptyList())
    val uiState by devicesViewModel.uiState.collectAsState()
    val favorites by favDevViewModel.getFavoritesId().collectAsState(initial = emptyList())

    var selectedDeviceId by remember { mutableStateOf<String?>(null) }
    var expandedDeviceId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = stringResource(id = R.string.home),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            modifier = Modifier.padding(6.dp)

        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = md_theme_light_coffee
                ),
                modifier = Modifier
                    .customShadow(
                        borderRadius = 10.dp,
                        offsetY = 6.dp,
                        offsetX = 6.dp,
                        spread = 3f
                    ).height(200.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, Color.Black)
            ){
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = stringResource(id = R.string.recent_activity),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
                if (recents.isEmpty()){
                    Row(modifier = modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_activity),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }

                else{
                    Row(modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        LazyColumn {
                            items(recents) { event ->
                                Box(modifier = Modifier.padding(10.dp)) {
                                    EventCard(event = event)
                                }
                            }
                        }
                    }
                }

            }
        }

        Row(
            modifier = Modifier.padding(6.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = md_theme_light_background
                ),
                modifier = Modifier
                    .customShadow(
                        borderRadius = 10.dp,
                        offsetY = 6.dp,
                        offsetX = 6.dp,
                        spread = 3f
                    ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, Color.Black)
            ){
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = stringResource(id = R.string.favorites),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.devices.filter { device -> device.id in favorites }) { device ->
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


