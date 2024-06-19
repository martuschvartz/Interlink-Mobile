package com.example.interlink.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.model.Device
import com.example.interlink.model.Lamp
import com.example.interlink.model.Status
import com.example.interlink.ui.InterlinkApp
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.LampUiState
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.getViewModelFactory

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    lampViewModel: LampViewModel = viewModel(factory = getViewModelFactory())
){

    val uiState by viewModel.uiState.collectAsState()
    val uiLampState by lampViewModel.uiState.collectAsState()

//    Box(
//        modifier = modifier
//    ){
//        Text(text = "Devices $uiState.devices, $uiLampState", color = Color.Black)
//    }

    DeviceList(devices = uiState.devices, lamp = uiLampState)
}

@Composable
fun DeviceList(devices: List<Device>, lamp: LampUiState) {
   Column(
        modifier = Modifier.fillMaxSize()
    ) {
        devices.forEach { device ->
            DeviceItem(device = device)
        }
       if (lamp.currentDevice != null)
       Text(text = lamp.currentDevice.name, color = Color.Black)

    }

}

@Composable
fun DeviceItem(device: Device) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle item click */ },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = device.name, color=Color.Black, style = MaterialTheme.typography.titleLarge)
        }
    }
}