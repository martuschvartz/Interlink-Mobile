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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Ac
import com.example.interlink.model.Status
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.AcViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun AcActions(
    acDevice : Ac,
    acViewModel: AcViewModel
) {

    val status = when(acDevice.status){
        Status.ON -> stringResource(id = R.string.acOn)
        Status.OFF -> stringResource(id = R.string.acOff)
        else -> null
    }

    val acModes= listOf(
        Pair(Pair("cool", stringResource(id = R.string.cool)), Icons.Default.AcUnit),
        Pair(Pair("heat", stringResource(id = R.string.heat)), Icons.Default.WbSunny),
        Pair(Pair("fan", stringResource(id = R.string.fan)), Icons.Default.Air)
    )

    val acBladesV= listOf(
        Pair(Pair("auto", "auto"), null),
        Pair(Pair("22", "22"), null),
        Pair(Pair("45", "45"), null),
        Pair(Pair("67", "67"), null),
        Pair(Pair("90", "90"), null)
    )

    val acBladesH= listOf(
        Pair(Pair("auto", "auto"), null),
        Pair(Pair("-90", "-90"), null),
        Pair(Pair("-45", "-45"), null),
        Pair(Pair("0", "0"), null),
        Pair(Pair("45", "45"), null),
        Pair(Pair("90", "90"), null)
    )

    val acSpeed= listOf(
        Pair(Pair("auto", "auto"), null),
        Pair(Pair("25", "25"), null),
        Pair(Pair("50", "50"), null),
        Pair(Pair("75", "75"), null),
        Pair(Pair("100", "100"), null)
    )

    // Para display
    var temperatureDisplay by remember { mutableIntStateOf(acDevice.temperature) }

    val actionButtonTitle : String
    val actionButtonColor : Color
    val actionButtonTextColor: Color

    if(acDevice.status == Status.OFF){
        actionButtonTitle = stringResource(id = R.string.onAction)
        actionButtonColor = md_theme_light_intergreen
        actionButtonTextColor = Color.Black
    }
    else{
        actionButtonTitle = stringResource(id = R.string.offACtion)
        actionButtonColor = md_theme_light_interred
        actionButtonTextColor = Color.White
    }

    Column{

        // Row de Estado
        Row(
            modifier = Modifier
                .padding(10.dp)
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
                    containerColor = actionButtonColor
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(3.dp, Color.Black),
                onClick = {
                    if(acDevice.status == Status.OFF){
                        acViewModel.turnOn()
                    }
                    if(acDevice.status == Status.ON){
                        acViewModel.turnOff()
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
                        color= actionButtonTextColor,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }

        // Row de Modo y Temperatura
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.mode),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(1.dp))
                SelectTextField(
                    showIcon = true,
                    initialValue = Pair(Pair(acDevice.mode, ""), acDevice.getImageVector()),
                    options = acModes,
                    onValueChanged = { selectedString ->
                        when (selectedString) {
                            "cool" -> acViewModel.setMode("cool")
                            "heat" -> acViewModel.setMode("heat")
                            "fan" -> acViewModel.setMode("fan")
                            else -> {}
                        }
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.temp)+" (°C)",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(1.dp))
                Card(
                    modifier = Modifier
                        .width(121.dp)
                        .height(100.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_background,
                        contentColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, md_theme_light_coffee),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$temperatureDisplay°",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Column {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        acViewModel.setTemperature(acDevice.temperature + 1)
                                        temperatureDisplay += 1
                                    },
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = null
                            )
                            Icon(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        acViewModel.setTemperature(acDevice.temperature - 1)
                                        temperatureDisplay -= 1
                                    },
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }

                }
            }
        }

        // Row de BladesV y BladesH
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.verticalBlades),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(1.dp))
                SelectTextField(
                    showIcon = false,
                    initialValue = Pair(Pair(acDevice.verticalSwing, acDevice.verticalSwing), null),
                    options = acBladesV,
                    onValueChanged = { selectedString ->
                        acViewModel.setVerticalSwing(selectedString)
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.horizontalBlades),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(1.dp))
                SelectTextField(
                    showIcon = false,
                    initialValue = Pair(Pair(acDevice.horizontalSwing, acDevice.horizontalSwing), null),
                    options = acBladesH,
                    onValueChanged = { selectedString ->
                        acViewModel.setHorizontalSwing(selectedString)
                    }
                )
            }
        }

        // Row de Speed
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.fan),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(1.dp))
                SelectTextField(
                    showIcon = false,
                    initialValue = Pair(Pair(acDevice.fanSpeed, acDevice.fanSpeed), null),
                    options = acSpeed,
                    onValueChanged = { selectedString ->
                        acViewModel.setFanSpeed(selectedString)
                    }
                )
            }

        }
    }

}

@Preview
@Composable
fun previewww3(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.temp)+" (°C)",
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.padding(1.dp))
        Card(
            modifier = Modifier
                .width(121.dp)
                .height(100.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = md_theme_light_background,
                contentColor = Color.Black,
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, md_theme_light_coffee),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "24.°",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Column {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }

        }
    }
}