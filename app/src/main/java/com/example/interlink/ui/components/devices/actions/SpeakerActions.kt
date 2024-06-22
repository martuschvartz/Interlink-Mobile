package com.example.interlink.ui.components.devices.actions

import SelectTextField
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
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
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

    // Generos
    val genres = listOf(
        Pair(Pair("classical", stringResource(id = R.string.clasical)), null),
        Pair(Pair("country", stringResource(id = R.string.country)), null),
        Pair(Pair("dance",stringResource(id = R.string.club)),null),
        Pair(Pair("latina",stringResource(id = R.string.latina)),null),
        Pair(Pair("pop",stringResource(id = R.string.pop)),null),
        Pair(Pair("rock",stringResource(id = R.string.rock)),null)
    )

    // Cancion
    val text = remember(speakerDevice.song?.title) {
        speakerDevice.song?.title?.let {
            if (it.length <= 19) it else it.substring(0, 17) + "..."
        } ?: ""
    }

    // Pausa
    var paused by remember { mutableStateOf(speakerDevice.status == Status.PAUSED) }

    val pauseButtonIcon : ImageVector
    val pauseButtonColor : Color

    if(paused){
        pauseButtonIcon = Icons.Default.PlayArrow
        pauseButtonColor = md_theme_light_intergreen
    }else{
        pauseButtonIcon = Icons.Default.Pause
        pauseButtonColor = md_theme_light_interred
    }

    // Barra de progreso

    var progressPercentage by remember { mutableFloatStateOf(0f) }
    progressPercentage = speakerDevice.song?.let {
        percentageTimePassed(
            maxTime = it.duration,
            currentTime = it.progress
        )
    } ?: 0f

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
                    else{
                        speakerViewModel.stop()
                        paused = false
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
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(id = R.string.genre),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )

            SelectTextField(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp),
                showIcon = false,
                initialValue = Pair(Pair(speakerDevice.genre,speakerDevice.genre),null),
                options = genres,
                onValueChanged = { selectedString ->
                    speakerViewModel.setGenre(selectedString)
                }
            )
        }

        // row de canciones
        if(speakerDevice.status != Status.STOPPED){
            Row(
                modifier = Modifier.padding(5.dp)
            ){
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_coffee
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {

                        // Titulo de la cancion
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ){
                            OutlinedCard(
                                modifier = Modifier
                                    .customShadow(
                                        borderRadius = 10.dp,
                                        offsetY = 8.dp,
                                        offsetX = 5.dp,
                                        spread = 3f
                                    ),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = md_theme_light_background
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(3.dp, Color.Black)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = text,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }

                        // barra de progreso
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 3.dp)
                        ){
                            LinearProgressIndicator(
                                progress = { progressPercentage },
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                trackColor = Color.Gray,
                                strokeCap = StrokeCap.Square,
                                gapSize = 0.dp,

                            )
                        }

                        // tiempos
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding( bottom = 10.dp)
                        ){
                            speakerDevice.song?.let {
                                Text(
                                    text = it.progress,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            speakerDevice.song?.let {
                                Text(
                                    text = it.duration,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }


                        // botones de control
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ){
                            OutlinedCard(
                                modifier = Modifier
                                    .customShadow(
                                        borderRadius = 10.dp,
                                        offsetY = 8.dp,
                                        offsetX = 5.dp,
                                        spread = 3f
                                    ),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = md_theme_light_intergreen
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(3.dp, Color.Black),
                                onClick = {
                                    speakerViewModel.previousSong()
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SkipPrevious,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(50.dp)
                                    )
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
                                    containerColor = pauseButtonColor
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(3.dp, Color.Black),
                                onClick = {
                                    if (speakerDevice.status == Status.PAUSED) {
                                        speakerViewModel.resume()
                                        paused = false
                                    }
                                    if (speakerDevice.status == Status.PLAYING) {
                                        speakerViewModel.pause()
                                        paused = true
                                    }
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = pauseButtonIcon,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(50.dp)
                                    )
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
                                    containerColor = md_theme_light_intergreen
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(3.dp, Color.Black),
                                onClick = {
                                    speakerViewModel.nextSong()
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SkipNext,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(50.dp)
                                    )
                                }
                            }


                        }
                    }
                }
            }
        }

        // row de playlist

    }
}


@Composable
fun timeToSeconds(time: String): Int {
    val (minutes, seconds) = time.split(":").map { it.toInt() }
    return minutes * 60 + seconds
}

@Composable
fun percentageTimePassed(maxTime: String, currentTime: String): Float {
    val maxTimeInSeconds = timeToSeconds(maxTime)
    val currentTimeInSeconds = timeToSeconds(currentTime)

    if (maxTimeInSeconds == 0) {
        return 0f // Prevent division by zero
    }

    return (currentTimeInSeconds.toFloat() / maxTimeInSeconds.toFloat())
}

