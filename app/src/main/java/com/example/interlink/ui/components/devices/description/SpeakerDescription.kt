package com.example.interlink.ui.components.devices.description

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Speaker
import com.example.interlink.model.Status

@Composable
fun SpeakerDescription(
    SpeakerDevice : Speaker
){
    if (SpeakerDevice.song!= null){
        Text(text = "Cancion: ${SpeakerDevice.song}", color=Color.Black, style = MaterialTheme.typography.bodyMedium)
    }
    else{
        Text(text = "No hay tal cancion lol", color=Color.Black, style = MaterialTheme.typography.bodyMedium)
    }
}