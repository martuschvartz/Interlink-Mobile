package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.interlink.model.Ac

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.model.Alarm

import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp

import com.example.interlink.model.Status
import com.example.interlink.ui.InterlinkApp
import com.example.interlink.ui.devices.AcViewModel

import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.devices.AlarmViewModel

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
){

    val uiState by viewModel.uiState.collectAsState()
    val uiLampState by lampViewModel.uiState.collectAsState()
    val uiDoorState by doorViewModel.uiState.collectAsState()
    var selectedDeviceId : String? = null

    Column (
        modifier = modifier.fillMaxSize()
    ) {


        // Esta primera funcion es lo primero que dejaria, mas abajo vas a ver porq en DeviceList()
        DeviceList(devices = uiState.devices){ device ->
            // Algo copado q encontre es este when() que funciona igual a un switch, lo re podes usar dentro de deviceCard en si :D!
            when(device.type){
                DeviceType.LAMP -> lampViewModel.setCurrentDevice(device as Lamp)
                DeviceType.DOOR -> doorViewModel.setCurrentDevice(device as Door)
                DeviceType.AC -> acViewModel.setCurrentDevice(device as Ac)
                DeviceType.ALARM -> alarmViewModel.setCurrentDevice(device as Alarm)
                else -> {}
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Test controls",  color=Color.Black, style = MaterialTheme.typography.titleLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = { alarmViewModel.armStay("1234") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "arm", color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = { alarmViewModel.disarm("1234") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "disarm",  color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = { alarmViewModel.changeSecurityCode("1234", "0000") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Change", color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = { alarmViewModel.changeSecurityCode("0000", "1234") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "ChangeBack",  color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }

    }



}

@Composable
fun DeviceList(devices: List<Device>, onDeviceClick: (Device) -> Unit) {
   LazyColumn {
        items(devices) { device ->
            Box(modifier = Modifier.padding(10.dp)){
                DeviceCard(device = device , onClick = { onDeviceClick(device) })
            }
        }
    }

}


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

