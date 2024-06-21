package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Door
import com.example.interlink.model.Status
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_intergrey
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun DoorActions(
    doorDevice : Door,
    doorViewModel : DoorViewModel
){
    val status = when(doorDevice.status){
        Status.OPENED -> stringResource(id = R.string.doorOpened)
        Status.CLOSED -> stringResource(id = R.string.doorClosed)
        else -> null
    }

    val locked = when(doorDevice.lock){
        "locked" -> stringResource(id = R.string.doorLocked)
        "unlocked" -> stringResource(id = R.string.doorUnlocked)
        else -> null
    }

    Column{
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.state),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (status != null) {
                        Text(
                            text = status,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            if (doorDevice.status == Status.OPENED) {
                // boton de cerrar
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_interred,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel.close()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.closeAction),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (doorDevice.status == Status.CLOSED) {
                // boton de abrir que abra la puerta, enabled si la puerta está destrabada, si no no se puede tocar
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_intergreen,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = md_theme_light_intergrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel.open()
                    },
                    enabled = doorDevice.lock == "unlocked"
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.openAction),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.doorLock),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (locked != null) {
                        Text(
                            text = locked,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            if (doorDevice.lock == "locked") {
                // boton de destrabar
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_interred,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel.unlock()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.unlockAction),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (doorDevice.lock == "unlocked") {
                // boton de trabar que traba la puerta, enabled si la puerta está cerrada, si no no se puede tocar
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_intergreen,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = md_theme_light_intergrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel.lock()
                    },
                    enabled = doorDevice.status == Status.CLOSED
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.lockAction),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Previewww(){

    val door = Door("1234", "puerta", Status.CLOSED, "locked")

    val status = when(door.status){
        Status.OPENED -> stringResource(id = R.string.doorOpened)
        Status.CLOSED -> stringResource(id = R.string.doorClosed)
        else -> null
    }

    val locked = when(door.lock){
        "locked" -> stringResource(id = R.string.doorLocked)
        "unlocked" -> stringResource(id = R.string.doorUnlocked)
        else -> null
    }

    Column(
      modifier = Modifier
          .size(400.dp),
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.state),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (status != null) {
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
                    containerColor = md_theme_light_interred,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, Color.Black),
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.openAction),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (locked != null) {
                        Text(
                            text = locked,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (locked != null) {
                        Text(
                            text = locked,
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
                        containerColor = md_theme_light_interred,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),

                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.lockAction),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }


        }
    }

}
