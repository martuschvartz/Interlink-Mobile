package com.example.interlink.ui.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.interlink.ui.theme.md_theme_light_intergreen
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun AlarmSwitch(

) {
    // Por ahora va un switch de on, es un placeholder para ver si funciona

    var openAlertDialog by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }

    // crear uno custom
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            openAlertDialog = !openAlertDialog
        },
        colors = SwitchDefaults.colors(
            checkedTrackColor = md_theme_light_intergreen,
            uncheckedTrackColor = md_theme_light_interred
        ),
    )

    // lógica del Alarm dialog, me voy a armar uno custom

    if(openAlertDialog){
        // acá va el dialog custom
    }

}