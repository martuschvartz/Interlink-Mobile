package com.example.interlink.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.SensorDoor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Blinds
import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Speaker
import com.example.interlink.ui.components.devices.actions.AcActions
import com.example.interlink.ui.components.devices.actions.AlarmActions
import com.example.interlink.ui.components.devices.actions.BlindsActions
import com.example.interlink.ui.components.devices.actions.DoorActions
import com.example.interlink.ui.components.devices.actions.SpeakerActions
import com.example.interlink.ui.components.devices.description.AcDescription
import com.example.interlink.ui.components.devices.description.AlarmDescription
import com.example.interlink.ui.components.devices.description.BlindsDescription
import com.example.interlink.ui.components.devices.description.DoorDescription
import com.example.interlink.ui.components.devices.description.SpeakerDescription
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.devices.SpeakerViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee
import kotlinx.coroutines.launch


// va a recibir el contenido según el device (tanto como info básica o la info completa)
// y los controles, y si el device asociado a la device card es el
@Composable
fun <T : Device> DeviceCard(
    currentDevice: Boolean,
    device: T,
    viewModel: ViewModel?,
    landscape: Boolean,
    expanded: Boolean = false,
    favDevViewModel : FavoritesEntryViewModel,
    onClick: (Device) -> Unit
){

    Log.d("DEBUG", "en Device card me llega landscape como $landscape")
    val coroutineScope = rememberCoroutineScope()

    // por default la card arranca sin ser favorita
    val isFav by favDevViewModel.isFavoriteDevice(device.id!!).collectAsState(initial = false)

    val icon = when(device.type){
        DeviceType.LAMP -> Icons.Default.Lightbulb
        DeviceType.SPEAKER -> Icons.Default.Speaker
        DeviceType.BLINDS -> Icons.Default.Blinds
        DeviceType.ALARM -> Icons.Default.Notifications
        DeviceType.DOOR -> Icons.Outlined.SensorDoor
        DeviceType.AC -> Icons.Outlined.AcUnit
    }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = md_theme_light_coffee
            ),
            modifier = Modifier
                .customShadow(
                    borderRadius = 10.dp,
                    offsetY = 6.dp,
                    offsetX = 6.dp,
                    spread = 3f
                ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = md_theme_light_background
                    ),
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 6.dp,
                            offsetX = 6.dp,
                            spread = 3f
                        )
                        .width(340.dp)
                        .clickable {
                            onClick(device)
                        },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp),
                    ) {
                        // Título e ícono
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp),
                                        tint = Color.Black
                                    )
                                    Spacer(modifier = Modifier.padding(1.dp))
                                    Text(
                                        text = device.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                }
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isFav) {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    device.id?.let {
//                                                        Log.d("DEBUG", "Sacamos: ${device.id}")
                                                        favDevViewModel.deleteFavorite(it)
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(width = 35.dp, height = 37.dp),
                                                tint = Color(0xFFFFB703),
                                            )
                                        }
                                    }
                                    else {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    device.id?.let {
//                                                        Log.d("DEBUG", "Agregamos: ${device.id}")
                                                        favDevViewModel.insertFavorite(it)
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.StarOutline,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(width = 35.dp, height = 37.dp),
                                                tint = Color.Black,
                                            )
                                        }
                                    }

                                    if (expanded) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowUp,
                                            contentDescription = null,
                                            modifier = Modifier.size(37.dp),
                                            tint = Color.Black
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            modifier = Modifier.size(37.dp),
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        // Acciones de los dispositivos
                        if (expanded && currentDevice) {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp),
                            ){
                                when (device.type) {
                                    DeviceType.LAMP -> {}
                                    DeviceType.SPEAKER -> SpeakerActions(device as Speaker, viewModel as SpeakerViewModel, landscape)
                                    DeviceType.BLINDS -> BlindsActions(device as Blinds, viewModel as BlindsViewModel, landscape)
                                    DeviceType.ALARM -> AlarmActions(device as Alarm, viewModel as AlarmViewModel, landscape)
                                    DeviceType.DOOR -> DoorActions(device as Door, viewModel as DoorViewModel, landscape)
                                    DeviceType.AC -> AcActions(device as  Ac, viewModel as AcViewModel, landscape)
                                }
                            }
                        }
                    }
                }

                if (!expanded) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .width(340.dp)
                    ) {
                        when(device.type){
                            DeviceType.LAMP -> {}
                            DeviceType.SPEAKER -> SpeakerDescription(device as Speaker)
                            DeviceType.BLINDS -> BlindsDescription(device as Blinds)
                            DeviceType.ALARM -> AlarmDescription(device as Alarm)
                            DeviceType.DOOR -> DoorDescription(device as Door)
                            DeviceType.AC -> AcDescription(device as Ac)
                        }
                    }
                }

            }
        }

}