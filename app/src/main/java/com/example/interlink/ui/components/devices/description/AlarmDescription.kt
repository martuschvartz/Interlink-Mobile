package com.example.interlink.ui.components.devices.description

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.interlink.R
import com.example.interlink.model.Alarm
import com.example.interlink.model.Status

@Composable
fun AlarmDescription(
    alarmDevice : Alarm
){
    val status = when(alarmDevice.status){
        Status.ARMEDSTAY -> stringResource(id = R.string.alarmOnLocal)
        Status.ARMEDAWAY -> stringResource(id = R.string.alarmOnRemote)
        Status.DISARMED -> stringResource(id = R.string.alarmOff)
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
    }
}