package com.example.interlink.ui.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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

    Column (
        modifier = modifier.fillMaxSize()
    ) {

        DeviceList(devices = uiState.devices, lamp = uiLampState, onDeviceClick = { device ->
            if (device is Lamp) {
                lampViewModel.setCurrentDevice(device)
            }
        })


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { lampViewModel.turnOn() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Turn On", color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = { lampViewModel.turnOff() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Turn Off",  color=Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiLampState.currentDevice != null)
            Text("Seleccionado: ${uiLampState.currentDevice!!.name}", color=Color.Black, style = MaterialTheme.typography.titleLarge)

    }



}

@Composable
fun DeviceList(devices: List<Device>, lamp: LampUiState, onDeviceClick: (Device) -> Unit) {
   LazyColumn {
        items(devices) { device ->
            DeviceItem(device = device, onClick = { onDeviceClick(device) })
        }
    }

}

@Composable
fun DeviceItem(device: Device, onClick: () -> Unit) {
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
}