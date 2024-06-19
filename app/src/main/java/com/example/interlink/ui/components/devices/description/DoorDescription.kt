package com.example.interlink.ui.components.devices.description

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.interlink.model.Door

@Composable
fun DoorDescription(
    doorDevice : Door
){
    Row {
        Text(
            text = "${doorDevice.status}"
        )
        Text(
            text = "$"
        )
    }
}