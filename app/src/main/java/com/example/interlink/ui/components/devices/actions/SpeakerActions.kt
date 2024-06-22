package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Speaker
import com.example.interlink.model.Status
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.SpeakerViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun SpeakerActions(
    speakerDevice : Speaker,
    speakerViewModel: SpeakerViewModel
){

    // Estados
    val status = when(speakerDevice.status){
        Status.PLAYING -> stringResource(id = R.string.playing)
        Status.STOPPED -> stringResource(id = R.string.stopped)
        Status.PAUSED -> stringResource(id = R.string.paused)
        else -> null
    }

    val actionButtonTitle : String
    val actionButtonTextColor : Color
    val actionButtonColor : Color

    if(speakerDevice.status == Status.PLAYING || speakerDevice.status == Status.PAUSED){
        actionButtonColor = md_theme_light_interred
        actionButtonTextColor = Color.White
        actionButtonTitle = stringResource(id = R.string.stopAction)
    }
    else {
        actionButtonColor = md_theme_light_intergreen
        actionButtonTextColor = Color.Black
        actionButtonTitle = stringResource(id = R.string.playAction)
    }


    // si cada 1 segundo hace un update de los device, puedo tener una variable mutablestateof
    // que sea el porcentaje de la cancion pasada, se hace mediante un calculo con la duracion
    // total de la cancion y el tiempo transcurrido. O sea a esa variable le asigno el resultado
    // de la funcion supongo, porque se va a ir updateando todo el tiempo



    Column{

        // Row de estado
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = R.string.state),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if(status != null){
                        Text(
                            text = status,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            OutlinedCard(
                modifier = Modifier
                    .customShadow(
                        borderRadius = 10.dp,
                        offsetY = 8.dp,
                        offsetX = 5.dp,
                        spread = 3f
                    ),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = actionButtonColor
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, Color.Black),
                onClick = {
                    if(speakerDevice.status == Status.STOPPED){
                        speakerViewModel.play()
                    }
                    if(speakerDevice.status == Status.PLAYING){
                        speakerViewModel.stop()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = actionButtonTitle,
                        color = actionButtonTextColor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        // Row de volumen
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(id = R.string.volume),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedCard(
                modifier = Modifier
                    .width(121.dp)
                    .height(100.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = md_theme_light_background,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, md_theme_light_coffee)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 5.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = speakerDevice.volume.toString(),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Column {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                           speakerViewModel.setVolume(speakerDevice.volume + 1)
                                },
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )

                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    speakerViewModel.setVolume(speakerDevice.volume - 1)
                                },
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        // Row de género


        // row de canciones


        // row de playlist

    }
}


