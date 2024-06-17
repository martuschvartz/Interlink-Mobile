package com.example.interlink.ui.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun HomePage(
    modifier: Modifier = Modifier
){
    Text(text = "Home", color = Color.Black)
}