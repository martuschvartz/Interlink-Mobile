package com.example.interlink.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.DeviceType
import com.example.interlink.remote.model.RemoteEvent
import com.example.interlink.ui.theme.md_theme_light_coffee
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun EventCard(event : RemoteEvent) {
    val icon = Icons.Default.Notifications
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
    ){
        Column(
            modifier = Modifier
                .padding(10.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
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
                            text = event.device.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )


                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = eventDescription(event = event),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        val instant = parseTimestamp(event.timestamp)
                        val formattedTimestamp = formatTimestamp(instant!!)
                        Text(
                            text = formattedTimestamp,
                            style = MaterialTheme.typography.bodyMedium,
//                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun eventDescription(event: RemoteEvent) : String{
    var toRet : String = ""
    val argsObject = event.args.asJsonObject
    when(event.device.type){
        DeviceType.DOOR -> {
            when (event.event) {
                "statusChanged" -> {
                    when(argsObject["newStatus"].asString){
                        "opened" -> toRet = stringResource(id = R.string.openedDoor)
                        "closed" -> toRet = stringResource(id = R.string.closedDoor)
                    }
                }
                "lockChanged" -> when(argsObject["newLock"].asString){
                    "locked" -> toRet = stringResource(id = R.string.lockedDoor)
                    "unlocked" -> toRet = stringResource(id = R.string.unlockedDoor)
                }
            }
        }
        DeviceType.ALARM -> when(argsObject["newStatus"].asString){
            "armedStay" -> toRet = stringResource(id = R.string.armedAlarm)
            "armedAway" -> toRet = stringResource(id = R.string.armedAlarm)
            "disarmed" -> toRet = stringResource(id = R.string.disarmedAlarm)
        }
        DeviceType.BLINDS-> when(argsObject["newStatus"].asString){
            "opened" -> toRet = stringResource(id = R.string.openedBlinds)
            "closed" -> toRet = stringResource(id = R.string.closedBlinds)
            "opening" -> toRet = stringResource(id = R.string.openingBlinds)
            "closing" -> toRet = stringResource(id = R.string.closingBlinds)
        }

        else -> {}
    }

    return toRet
}

fun parseTimestamp(timestamp: String): Instant? {
    return try {
        Instant.parse(timestamp)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    }
}

fun formatTimestamp(instant: Instant, pattern: String = "HH:mm"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}