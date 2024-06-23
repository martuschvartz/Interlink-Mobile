package com.example.interlink.ui.components.devices.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AlarmActions(
    alarmDevice: Alarm,
    alarmViewModel: AlarmViewModel,
){
    val status = when(alarmDevice.status){
        Status.ARMEDSTAY -> stringResource(id = R.string.alarmOnLocal)
        Status.ARMEDAWAY -> stringResource(id = R.string.alarmOnRemote)
        Status.DISARMED -> stringResource(id = R.string.alarmOff)
        else -> null
    }

    var insertCodeDialog by remember { mutableStateOf(false) }
    var changeCodeDialog by remember { mutableStateOf(false) }
    var stateFunction by remember { mutableStateOf("") }

    var response by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .padding(10.dp)
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
            if(alarmDevice.status == Status.DISARMED) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
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
                            insertCodeDialog = true
                            stateFunction = "armStay"
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.turnOnLocally),
                                color = Color.Black,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(3.dp))

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
                            insertCodeDialog = true
                            stateFunction = "armAway"
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.turnOnRemotely),
                                color = Color.Black,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }
            else{
                OutlinedCard(
                    modifier = Modifier
                        .customShadow(
                            borderRadius = 10.dp,
                            offsetY = 8.dp,
                            offsetX = 5.dp,
                            spread = 3f
                        ),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = md_theme_light_interred
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        insertCodeDialog = true
                        stateFunction = "disarm"
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.offACtion),
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }

            if(insertCodeDialog){
                when(stateFunction){
                    "disarm" ->{
                            InsertCodeDialog(
                                onDismissRequest = {
                                    insertCodeDialog = false
                                },
                                alarmViewModel = alarmViewModel,
                                coroutineScope = coroutineScope,
                            ) { codeEntered ->
                                alarmViewModel.disarm(codeEntered)
                            }
                    }
                    "armAway" ->{
                        InsertCodeDialog(
                            onDismissRequest = {
                                insertCodeDialog = false
                            },
                            alarmViewModel = alarmViewModel,
                            coroutineScope = coroutineScope
                        ) {codeEntered ->
                             alarmViewModel.armAway(codeEntered)
                        }
                    }
                    "armStay" -> {
                        InsertCodeDialog(
                            onDismissRequest = {
                                insertCodeDialog = false
                            },
                            alarmViewModel = alarmViewModel,
                            coroutineScope = coroutineScope,
                        ) {codeEntered ->
                            alarmViewModel.armStay(codeEntered)
                        }
                    }
                }
            }

        }
            Row(
                modifier = Modifier
                    .padding(10.dp)
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
                        containerColor = md_theme_light_intergreen
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.Black),
                    onClick = {
                        changeCodeDialog = true
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.changeCode),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }

                if (changeCodeDialog) {
                    ChangeCodeDialog(
                        onDismissRequest = { changeCodeDialog = false },


                        alarmViewModel = alarmViewModel,
                        coroutineScope = coroutineScope
                    ) { oldCode, newCode ->
                        alarmViewModel.changeSecurityCode(oldCode, newCode)


                    }
                }
            }
    }
}


@Composable
fun ChangeCodeDialog(
    onDismissRequest: () -> Unit,
    alarmViewModel: AlarmViewModel,
    coroutineScope : CoroutineScope,
    onCodeChanged: (String, String) -> CompletableDeferred<Boolean?>
){
    Dialog(onDismissRequest){

        // variables intermedias
        var tempCodeEntered by remember { mutableStateOf("") }
        var tempNewCode by remember { mutableStateOf("") }

        var wrongNewCode by remember { mutableStateOf(false) }
        var wrongCodeEntered by remember { mutableStateOf(false) }

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
                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = stringResource(id = R.string.oldCodeLabel),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = tempCodeEntered,
                            onValueChange = {
                                tempCodeEntered = it
                                wrongCodeEntered = false
                            },
                            isError = wrongCodeEntered,
                            singleLine = true,
                            supportingText = {
                                if (wrongCodeEntered) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = R.string.enterAgain),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            trailingIcon = {
                                if (wrongCodeEntered)
                                    Icon(Icons.Filled.Error,null, tint = MaterialTheme.colorScheme.error)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
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
                            )
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = stringResource(id = R.string.newCodeLabel),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = tempNewCode,
                            onValueChange = {
                                tempNewCode = it
                                wrongNewCode = false
                            },
                            isError = wrongNewCode,
                            singleLine = true,
                            supportingText = {
                                if (wrongNewCode) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = R.string.invalidCode),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            trailingIcon = {
                                if (wrongNewCode)
                                    Icon(Icons.Filled.Error,null, tint = MaterialTheme.colorScheme.error)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    wrongNewCode = tempNewCode.length != 4
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
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                // donde está el botón de confirmar,
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
                            coroutineScope.launch{
                                wrongCodeEntered = !(alarmViewModel.fetchNewVal { onCodeChanged(tempCodeEntered, tempNewCode) } !!)

                                if(!wrongCodeEntered) {
                                    onDismissRequest()
                                }
                            }
                        },
                        enabled = !wrongNewCode
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


@Composable
fun InsertCodeDialog(
    onDismissRequest: () -> Unit,
    alarmViewModel: AlarmViewModel,
    coroutineScope : CoroutineScope,
    onCodeEntered: (String) -> CompletableDeferred<Boolean?>
){
    Dialog(onDismissRequest){

        var wrongCode by remember { mutableStateOf(false) }
        var tempCodeEntered by remember { mutableStateOf("") }

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
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Row de Textfield
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ){
                    Column(
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = stringResource(id = R.string.insertCodeLabel),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = tempCodeEntered,
                            onValueChange = {
                                tempCodeEntered = it
                                wrongCode = false
                            },
                            isError = wrongCode,
                            singleLine = true,
                            supportingText = {
                                if (wrongCode) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = R.string.enterAgain),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            trailingIcon = {
                                if (wrongCode)
                                    Icon(Icons.Filled.Error,null, tint = MaterialTheme.colorScheme.error)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
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
                            )
                        )

                    }
                }

                // Row de Botón
                Row(
                    modifier = Modifier
                        .padding(10.dp)
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
                            coroutineScope.launch{
                                wrongCode = !(alarmViewModel.fetchNewVal { onCodeEntered(tempCodeEntered) } !!)

                                if(!wrongCode) {
                                    onDismissRequest()
                                }
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
                                text = stringResource(id = R.string.accept),
                                color= Color.Black,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
