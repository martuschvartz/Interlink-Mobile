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
    // si en la pagina no cambia la descripcion es porque aca lo puse como variable "no ref"

    var status = when(doorDevice.status){
        Status.OPENED -> stringResource(id = R.string.opened)
        Status.CLOSED -> stringResource(id = R.string.closed)
        else -> null
    }

    Row {
        if (status != null) {
            Text(
                text = status,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
                )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        if(doorDevice.lock == "locked"){
            Text(
                text = stringResource(id = R.string.locked),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (doorDevice.lock == "unlocked"){
            Text(
                text = stringResource(id = R.string.locked),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}