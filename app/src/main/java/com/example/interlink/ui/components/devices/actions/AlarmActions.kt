package com.example.interlink.ui.components.devices.actions

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.interlink.R
import com.example.interlink.model.Alarm
import com.example.interlink.model.Status
import com.example.interlink.ui.components.customShadow
import com.example.interlink.ui.devices.AlarmViewModel
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee
import com.example.interlink.ui.theme.md_theme_light_interblue
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_intergrey
import com.example.interlink.ui.theme.md_theme_light_interred
import kotlinx.coroutines.launch


@Composable
fun AlarmActions(
    alarmDevice: Alarm,
    alarmViewModel: AlarmViewModel
){
    val status = when(alarmDevice.status){
        Status.ARMEDSTAY -> stringResource(id = R.string.alarmOnLocal)
        Status.ARMEDAWAY -> stringResource(id = R.string.alarmOnRemote)
        Status.DISARMED -> stringResource(id = R.string.alarmOff)
        else -> null
    }

    var turnOnDialog by remember { mutableStateOf(false) }
    var insertCodeDialog by remember { mutableStateOf(false) }
    var changeCodeDialog by remember { mutableStateOf(false) }

    var codeEntered by remember { mutableStateOf("0000") }
    var response by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    val actionButtonTitle : String
    val actionButtonColor : Color
    val actionButtonTextColor: Color

    if(alarmDevice.status == Status.ARMEDSTAY || alarmDevice.status == Status.ARMEDAWAY){
        actionButtonTitle = stringResource(id = R.string.offACtion)
        actionButtonColor = md_theme_light_interred
        actionButtonTextColor = Color.White
    }
    else{
        actionButtonTitle = stringResource(id = R.string.onAction)
        actionButtonColor = md_theme_light_intergreen
        actionButtonTextColor = Color.Black
    }

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
                    if (status != null) {
                        Text(
                            text = status,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // acá iría la card de encender/apagar, que prende el primer dialog y manda
            // y manda la accion a hacer, que es
        }
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
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
                    changeCodeDialog = true
                }
            ){
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.changeCode),
                        color= Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            if(changeCodeDialog){
                ChangeCodeDialog(
                    onDismissRequest = { changeCodeDialog = false },
                    codeEntered = codeEntered,
                ) { oldCode, newCode ->
                    codeEntered = newCode

                    coroutineScope.launch {
                        response = alarmViewModel.fetchNewVal { alarmViewModel.changeSecurityCode(oldCode, newCode) }!!
                        Log.d("DEBUG", "Nos llega: ${response}")
                    }
                }
            }
        }
    }
}

//Ponerle tamaño fijo a la card
@Composable
fun ChangeCodeDialog(
    onDismissRequest: () -> Unit,
    codeEntered: String,
    onCodeChanged: (String, String) -> Unit
){
    Dialog(onDismissRequest){

        // variables intermedias
        var tempCodeEntered by remember { mutableStateOf(codeEntered) }
        var tempNewCode by remember { mutableStateOf("0000") }

        var codeEnteredEnable by remember { mutableStateOf(true) }
        var newCodeEnable by remember { mutableStateOf(true) }
        var buttonEnabled by remember { mutableStateOf(true) }

        buttonEnabled = (codeEnteredEnable && newCodeEnable)

        val focusManager = LocalFocusManager.current

        OutlinedCard(
            modifier = Modifier
                .customShadow(
                    borderRadius = 10.dp,
                    offsetY = 8.dp,
                    offsetX = 5.dp,
                    spread = 3f
                ),
            colors = CardDefaults.outlinedCardColors(
                containerColor = md_theme_light_background,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, Color.Black)
        ){
            Column(
                modifier = Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // donde van a estar los textfields
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Column(
                        verticalArrangement = Arrangement.Center
                    ){
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = tempCodeEntered,
                            onValueChange = {
                                tempCodeEntered = it
                                codeEnteredEnable = true
                            },
                            isError = !codeEnteredEnable,
                            singleLine = true,
                            supportingText = {
                                if (!codeEnteredEnable) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = R.string.enterAgain),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            trailingIcon = {
                                if (!codeEnteredEnable)
                                    Icon(Icons.Filled.Error,null, tint = MaterialTheme.colorScheme.error)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    codeEnteredEnable = (tempCodeEntered == codeEntered)
                                    focusManager.clearFocus()
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = md_theme_light_background,
                                unfocusedContainerColor = md_theme_light_background,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedBorderColor = md_theme_light_interblue,
                                unfocusedBorderColor = md_theme_light_coffee
                            ),
                            label = { stringResource(id = R.string.oldCodeLabel) }
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = tempNewCode,
                            onValueChange = {
                                tempNewCode = it
                                newCodeEnable = true
                            },
                            isError = !newCodeEnable,
                            singleLine = true,
                            supportingText = {
                                if (!newCodeEnable) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = R.string.invalidCode),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            trailingIcon = {
                                if (!newCodeEnable)
                                    Icon(Icons.Filled.Error,null, tint = MaterialTheme.colorScheme.error)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    newCodeEnable = (tempNewCode.length == 4)
                                    focusManager.clearFocus()
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = md_theme_light_background,
                                unfocusedContainerColor = md_theme_light_background,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedBorderColor = md_theme_light_interblue,
                                unfocusedBorderColor = md_theme_light_coffee
                            ),
                            label = { stringResource(id = R.string.newCodeLabel)}
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                // donde está el botón de confirmar, solo si no hay valor de error (?
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
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
                            containerColor = md_theme_light_intergreen,
                            disabledContainerColor = md_theme_light_intergrey,
                            contentColor = Color.Black,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(3.dp, Color.Black),
                        onClick = {
                            onDismissRequest()
                            onCodeChanged(tempCodeEntered, tempNewCode)
                        },
                        enabled = buttonEnabled
                    ) {
                        Column {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.accept),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}