package com.example.interlink.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.ui.theme.md_theme_light_interred

@Composable
fun DeleteNotifsActionButton(
    onClick : () -> Unit
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
            containerColor = md_theme_light_interred
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(3.dp, Color.Black),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.cleanPage),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}