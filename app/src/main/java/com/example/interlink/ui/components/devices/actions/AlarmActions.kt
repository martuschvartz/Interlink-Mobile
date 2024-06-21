package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.model.Alarm
import com.example.interlink.ui.devices.AlarmViewModel


@Composable
fun AlarmActions(
    alarmDevice: Alarm,
    alarmViewModel: AlarmViewModel
){
    // variable status ........
    var turnOnDialog by remember { mutableStateOf(false) }
    var turnOffDialog by remember { mutableStateOf(false) }
    var changeCodeDialog by remember { mutableStateOf(false) }

    val actionButtonTitle : String
    val actionButtonColor : Color
    val actionButtonTextColor: Color

    Column{
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
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
                    // acá iría el text con status
                }
            }

            // acá iría la card de encender/apagar, que prende el primer dialog y manda
            // y manda la accion a hacer, que es
        }
    }
}