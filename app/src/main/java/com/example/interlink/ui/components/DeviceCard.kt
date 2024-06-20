package com.example.interlink.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.ui.components.devices.actions.DoorAction
import com.example.interlink.ui.components.devices.description.DoorDescription
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee


// va a recibir el contenido según el device (tanto como info básica o la info completa)
// y los controles, y si el device asociado a la device card es el
@Composable
fun <T : Device, VM : ViewModel> DeviceCard(
    device: T,
    viewModel: VM?,
    onClick: (Device) -> Unit
){

    // viewModels

    // por default la card arranca sin expandirse
    var expanded by remember { mutableStateOf(false) }

    // por default la card arranca sin ser favorita
    var fav by remember { mutableStateOf(false) }

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
                        .width(320.dp)
                        .clickable {
                            expanded = !expanded
                            onClick(device)
                        },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp),
                    ) {
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
                                    if (fav) {
                                        IconButton(
                                            onClick = { fav = !fav }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(width = 35.dp, height = 37.dp),
                                                tint = Color(0xFFFFB703),
                                            )
                                        }
                                    } else {
                                        IconButton(
                                            onClick = { fav = !fav }
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

                        if (expanded) {
                            // aca va las accinoes de los dispositivos
                            Row(
                                modifier = Modifier
                                    .padding(5.dp),
                            ){
                                when (device.type) {
                                    DeviceType.LAMP -> {}
                                    DeviceType.SPEAKER -> {}
                                    DeviceType.BLINDS -> {}
                                    DeviceType.ALARM -> {}
                                    DeviceType.DOOR -> DoorAction(device as Door, viewModel as DoorViewModel)
                                    DeviceType.AC -> {}
                                }
                            }
                        }
                    }
                }

                if (!expanded) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .width(320.dp)
                    ) {
                        when(device.type){
                            DeviceType.LAMP -> {}
                            DeviceType.SPEAKER -> {}
                            DeviceType.BLINDS -> {}
                            DeviceType.ALARM -> {}
                            DeviceType.DOOR -> DoorDescription(device as Door)
                            DeviceType.AC -> {}
                        }
                    }
                }

            }
        }

}

/*
@Preview
@Composable
fun DeviceCardPreview(){
    Surface(
        modifier = Modifier
            .size(500.dp),
        color = Color.White,
    ) {
        val door = Door(id="1234",name = "puerta", status=Status.OPENED, lock = "locked")

        Box(
            modifier = Modifier.padding(10.dp),
        ){
            DeviceCard(door){

            }
        }

    }
}*/