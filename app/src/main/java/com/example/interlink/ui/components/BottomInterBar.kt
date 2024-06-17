package com.example.interlink.ui.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.navigation.AppNavigation
import com.example.interlink.ui.theme.md_theme_light_intergreen

@Composable
fun BottomInterBar(
    currentDestination: String?,
    onNavigate: (String) -> Unit
){

    val items = listOf(
        AppNavigation.HOME,
        AppNavigation.ACTIVITY,
        AppNavigation.DEVICES
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = stringResource(item.title)) },
                label = { Text(text = stringResource(item.title)) },
                alwaysShowLabel = true,
                selected = currentDestination == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }

   Surface(
        modifier = Modifier.wrapContentSize(),
        color = md_theme_light_intergreen
    ){
        HorizontalDivider(
            thickness = 3.dp,
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach {item ->
                CustomBarButton(
                    onClick = { onNavigate(item.route) },
                    destination = item,
                    selected = item.route == currentDestination
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomInterBarPrev(){
    BottomInterBar("home", {})
}