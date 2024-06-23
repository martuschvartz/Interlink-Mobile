package com.example.interlink.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.components.CustomBarButton
import com.example.interlink.ui.theme.md_theme_light_intergreen

@Composable
fun NavigationInterRail(
    currentDestination : String?,
    onNavigate: (String) -> Unit
){

    val items = listOf(
        AppNavigation.HOME,
        AppNavigation.DEVICES,
        AppNavigation.ACTIVITY
    )

    Box(
        modifier = Modifier.wrapContentSize()
    ){
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            color = md_theme_light_intergreen
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(104.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                items.forEach { item ->

                    Spacer(
                        modifier = Modifier
                            .padding(10.dp)
                    )

                    CustomBarButton(
                        onClick = { onNavigate(item.route) },
                        destination = item,
                        selected = item.route == currentDestination,
                        tablet = true,
                        showLabels = true
                    )
                }
            }

            VerticalDivider(
                modifier = Modifier.align(Alignment.BottomEnd),
                thickness = 3.dp,
                color = Color.Black
            )
        }
    }

}

@Preview
@Composable
fun NavigationInterRailPreview(){
    NavigationInterRail("home", {})
}