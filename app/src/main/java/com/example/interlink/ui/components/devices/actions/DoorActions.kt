package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_intergrey
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun DoorAction(
    doorDevice : Door,
    doorViewModel : DoorViewModel?
){
    val status = when(doorDevice.status){
        Status.OPENED -> stringResource(id = R.string.opened)
        Status.CLOSED -> stringResource(id = R.string.closed)
        else -> null
    }

    val locked = when(doorDevice.lock){
        "locked" -> stringResource(id = R.string.locked)
        "unlocked" -> stringResource(id = R.string.unlocked)
        else -> null
    }

    Column{
        Row(
            modifier = Modifier
                .padding(bottom = 5.dp)
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
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (status != null) {
                        Text(
                            text = status,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (doorDevice.status == Status.OPENED) {
                // boton de cerrar
                OutlinedButton(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 6.dp,
                            spread = 3f
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = md_theme_light_interred,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel?.close()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.close),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (doorDevice.status == Status.CLOSED) {
                // boton de abrir que abra la puerta, enabled si la puerta está destrabada, si no no se puede tocar
                OutlinedButton(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 6.dp,
                            spread = 3f
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = md_theme_light_intergreen,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = md_theme_light_intergrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel?.open()
                    },
                    enabled = doorDevice.lock == "unlocked"
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.open),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 5.dp)
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
                        text = stringResource(id = R.string.lock),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (locked != null) {
                        Text(
                            text = locked,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (doorDevice.lock == "locked") {
                // boton de destrabar
                OutlinedButton(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 6.dp,
                            spread = 3f
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = md_theme_light_interred,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel?.unlock()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
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
                OutlinedButton(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 6.dp,
                            spread = 3f
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = md_theme_light_intergreen,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = md_theme_light_intergrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        doorViewModel?.lock()
                    },
                    enabled = doorDevice.status == Status.CLOSED
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
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


@Preview
@Composable
fun DeviceCardPreview(){
    Surface(
        modifier = Modifier
            .size(500.dp),
        color = md_theme_light_background,
    ) {
        val door = Door(id="1234",name = "puerta", status=Status.OPENED, lock = "locked")

        Box(
            modifier = Modifier.padding(10.dp),
        ){
            DoorAction(door,null)
        }

    }
}