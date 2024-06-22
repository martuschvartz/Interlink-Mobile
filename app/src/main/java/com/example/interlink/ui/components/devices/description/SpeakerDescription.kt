package com.example.interlink.ui.components.devices.description

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.interlink.R
import com.example.interlink.model.Speaker
import com.example.interlink.model.Status

@Composable
fun SpeakerDescription(
    speakerDevice : Speaker
){

    Row {
        if (speakerDevice.status == Status.PLAYING || speakerDevice.status == Status.PAUSED) {
            Text(
                text = stringResource(id = R.string.playing) + ": ",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            speakerDevice.song?.let {
                Text(
                    text = it.title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if (speakerDevice.status == Status.PAUSED){
                Text(
                    text = " " + stringResource(id = R.string.paused),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        else{
            Text(
                text = stringResource(id = R.string.stopped),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}