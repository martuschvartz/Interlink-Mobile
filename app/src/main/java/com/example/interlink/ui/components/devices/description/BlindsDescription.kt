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
import com.example.interlink.model.Blinds
import com.example.interlink.model.Status

@Composable
fun BlindsDescription(
    blindsDevice : Blinds
){
    val status = when(blindsDevice.status){
        Status.OPENED -> stringResource(id = R.string.blindsOpened)
        Status.CLOSED -> stringResource(id = R.string.blindsClosed)
        Status.OPENING -> stringResource(id = R.string.openingAction)
        Status.CLOSING -> stringResource(id = R.string.closingAction)
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
            text = stringResource(id = R.string.blindsCurrentLevel),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(1.dp))
        Text(
            text = blindsDevice.currentLevel.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}