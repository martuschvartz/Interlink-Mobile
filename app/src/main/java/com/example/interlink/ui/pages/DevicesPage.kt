package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Blinds
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp
import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.getViewModelFactory

// el modifier default es la misma clase Modifier, sino es el que le paso

// Algo importante: Todo lo q esta adentro de model, remote y repository no es *necesario* q lo leas
// Si lo queres leer by all means go ahead asi entendes mas mejor q esta pasando con el api pero te deje las cosas para q
// esten lo mas simples posibles para q puedas meterle al ui y te despreocupes por el api
// Obvio q si algo esta mal avisame

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    lampViewModel: LampViewModel = viewModel(factory = getViewModelFactory()),
    doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
    acViewModel: AcViewModel = viewModel(factory = getViewModelFactory()),
    alarmViewModel: AlarmViewModel = viewModel(factory = getViewModelFactory()),
    blindsViewModel: BlindsViewModel = viewModel(factory = getViewModelFactory()),
){

    val uiState by viewModel.uiState.collectAsState()
    var selectedDeviceId : String? = null

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            // Trate de dejarte esto lo mas generico posible para q si cambias DeviceItem por DeviceCard (o como la quieras llamar) no sea dificil hacer el refractoring
            // Si no me dan mal los calculos solo tendrias que cambiar esto de aca para poner tus cards
            items(uiState.devices) { device ->
                Box(modifier = Modifier.padding(10.dp)){
                    val deviceViewModel = when(device.type){
                        DeviceType.LAMP -> lampViewModel
                        DeviceType.SPEAKER -> null
                        DeviceType.BLINDS -> blindsViewModel
                        DeviceType.ALARM -> alarmViewModel
                        DeviceType.DOOR -> doorViewModel
                        DeviceType.AC -> acViewModel
                    }
                    DeviceCard(
                        currentDevice = device.id == selectedDeviceId,
                        device = device,
                        viewModel = deviceViewModel
                    ) { device ->

                        selectedDeviceId = device.id
                        when(device.type){
                            DeviceType.LAMP -> lampViewModel.setCurrentDevice(device as Lamp)
                            DeviceType.DOOR -> doorViewModel.setCurrentDevice(device as Door)
                            DeviceType.BLINDS -> blindsViewModel.setCurrentDevice(device as Blinds)
                            DeviceType.ALARM -> alarmViewModel.setCurrentDevice(device as Alarm)
                            DeviceType.AC -> acViewModel.setCurrentDevice(device as Ac)
                            else -> {}
                        }
                    }
                }
            }
        }

    }

}

/*
@Composable
fun DeviceList(devices: List<Device>, onDeviceClick: (Device) -> Unit) {
   LazyColumn {
        items(devices) { device ->
            Box(modifier = Modifier.padding(10.dp)){
                DeviceCard(device = device, viewModel = , onClick = { onDeviceClick(device) })
            }
        }
    }

}
*/



/*
@Composable
fun <T : Device> DeviceItem(device: T, onClick: () -> Unit) {
    // Nada importante aca, solo q la carta es clickeable y q tiene un outline, no mas q eso
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = device.name, color=Color.Black, style = MaterialTheme.typography.titleLarge)
        }
    }
}*/

