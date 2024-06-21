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
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Status

@Composable
fun AlarmDescription(
    alarmDevice : Alarm
){
    val status = when(alarmDevice.status){
        Status.ON -> stringResource(id = R.string.acOn)
        Status.OFF -> stringResource(id = R.string.acOff)
        else -> null
    }

    Row {
        if (status != null) {
            Text(
                text = status,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "-",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = acDevice.temperature.toString()+"°",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}