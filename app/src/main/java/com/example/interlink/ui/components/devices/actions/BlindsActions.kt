package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Blinds
import com.example.interlink.model.Status
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.BlindsViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_intergrey
import com.example.interlink.ui.theme.md_theme_light_interred


@Composable
fun BlindsActions(
    blindsDevice : Blinds,
    blindsViewModel : BlindsViewModel,
    landscape : Boolean
){

    // Global
    val actionsModifier = if(landscape) Modifier.verticalScroll(rememberScrollState()) else Modifier

    val status = when(blindsDevice.status){
        Status.OPENED -> stringResource(id = R.string.blindsOpened)
        Status.CLOSED -> stringResource(id = R.string.blindsClosed)
        Status.OPENING -> stringResource(id = R.string.openingAction)
        Status.CLOSING -> stringResource(id = R.string.closingAction)
        else -> null
    }

    val lockAction by remember { mutableStateOf(false) }
    val actionButtonTitle : String
    val actionButtonColor : Color
    val actionButtonTextColor : Color

    if(blindsDevice.status == Status.CLOSING || blindsDevice.status == Status.OPENING){
        actionButtonTitle = stringResource(id = R.string.loading)
        actionButtonColor = md_theme_light_intergrey
        actionButtonTextColor = Color.White
    }
    else if(blindsDevice.status == Status.CLOSED){
        actionButtonTitle = stringResource(id = R.string.openAction)
        actionButtonColor = md_theme_light_intergreen
        actionButtonTextColor = Color.Black
    }
    else{
        actionButtonTitle = stringResource(id = R.string.closeAction)
        actionButtonColor = md_theme_light_interred
        actionButtonTextColor = Color.White
    }

    Column(
        modifier = actionsModifier
    ){
        Row(
            modifier = Modifier
                .padding(5.dp)
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
                            text = status +" (${blindsDevice.currentLevel}%)",
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
                enabled = !lockAction,
                onClick = {
                    if(blindsDevice.status == Status.CLOSED){
                        blindsViewModel.open()
                    }
                    if(blindsDevice.status == Status.OPENED){
                        blindsViewModel.close()
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


            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.blindsLevel),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Card(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_background,
                        contentColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = blindsDevice.level.toString(),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Column {
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { blindsViewModel.setLevel(blindsDevice.level + 10) },
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = null
                            )
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { blindsViewModel.setLevel(blindsDevice.level - 10) },
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }

                }
            }

    }
}

@Preview(showBackground = true)
@Composable
fun Previewww2(){

    val blinds = Blinds("1234", "puerta", Status.OPENED, 90, 0)

    var lockAction by remember { mutableStateOf(false) }
    val actionButtonTitle : String
    val actionButtonColor : Color
    val actionButtonContentColor : Color

    if(lockAction){
        actionButtonTitle = blinds.currentLevel.toString() + "%"
        actionButtonColor = md_theme_light_intergrey
        actionButtonContentColor = Color.White
    }
    else if(blinds.status == Status.CLOSED){
        actionButtonTitle = stringResource(id = R.string.openAction)
        actionButtonColor = Color.Green
        actionButtonContentColor = Color.Black
    }
    else{
        actionButtonTitle = stringResource(id = R.string.closeAction)
        actionButtonColor = Color.Red
        actionButtonContentColor = Color.White
    }

    Card(
        modifier = Modifier
            .customShadow(
                borderRadius = 10.dp,
                offsetY = 8.dp,
                offsetX = 5.dp,
                spread = 3f
            ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = md_theme_light_background,
            contentColor = Color.Black,
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(3.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = blinds.level.toString(),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Column {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

    }
}