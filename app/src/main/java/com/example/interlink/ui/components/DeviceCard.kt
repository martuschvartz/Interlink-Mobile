package com.example.interlink.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee


@Composable
fun DeviceCard(){

    // por default la variable arranca sin expandirse
    Card(
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_background
        ),
        modifier = Modifier
            .height(200.dp)
    ) {
        Text(text = "Device ")
    }

}


@Preview()
@Composable
fun DeviceCardPreview(){

    Card(
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_coffee,
        ),
        modifier = Modifier
            //.wrapContentHeight()
            .size(300.dp)



    ){

    }

    DeviceCard()
}