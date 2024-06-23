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
import com.example.interlink.model.Door
import com.example.interlink.model.Status

@Composable
fun DoorDescription(
    doorDevice : Door
){
    val status = when(doorDevice.status){
        Status.OPENED -> stringResource(id = R.string.doorOpened)
        Status.CLOSED -> stringResource(id = R.string.doorClosed)
        else -> null
    }

    val locked = when(doorDevice.lock){
        "locked" -> stringResource(id = R.string.doorLocked)
        else -> stringResource(id = R.string.doorUnlocked)
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
            text = locked,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}