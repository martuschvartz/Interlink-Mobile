package com.example.interlink.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.navigation.AppNavigation

@Composable
fun CustomBarButton(
    showLabels : Boolean,
    tablet: Boolean,
    selected: Boolean = false,
    onClick: () -> Unit,
    destination: AppNavigation
){

    val color: Color = if(selected){
                            Color.White
                        }
                        else {
                            Color.Black
                        }

    Box(
        modifier = Modifier.wrapContentHeight()
    ) {
        TextButton(
            onClick = onClick,
            ) {
            Column(
                modifier = Modifier
                    .height(60.dp)
            ){
                Icon(
                    imageVector = destination.icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(41.dp)
                )

                // Para la tablet, no muestra el texto de aquellos botones no seleccionados, para ladnscape directamente no lo muestra
                if(showLabels && (!tablet || selected)) {
                    Text(
                        text = stringResource(id = destination.title),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        color = color
                    )
                }
            }
        }
    }
}
