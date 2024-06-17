package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
    ){
        Text(text = "Devices", color = Color.Black)
    }
}