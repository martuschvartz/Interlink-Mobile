package com.example.interlink.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.interlink.ui.components.BottomInterBar
import com.example.interlink.ui.components.TopInterlinkBar
import com.example.interlink.ui.navigation.InterNavHost
import com.example.interlink.ui.theme.InterlinkTheme
import com.example.interlink.ui.theme.md_theme_light_background

// dejarle a bottom bar y riel por el divider, como la top bar

// usar box with constraints para tema responsive

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InterlinkApp() {
    InterlinkTheme {
        // Controladores de Navegación
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        BoxWithConstraints{
            if (maxWidth < 600.dp) {
                // custom bottom app bar en scaffold
                // a bottom app bar le paso la funcion que debe hacer cuando clickeo, como en el ejemplo del profe
                Scaffold(
                    topBar = { TopInterlinkBar() },
                    bottomBar = {
                        BottomInterBar(
                            currentDestination = currentDestination,
                        ) { route ->
                            navController.navigate(route) {
                                // Pop hasta la direccion de inicio inclusive
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },

                    modifier = Modifier.fillMaxHeight()
                ) {
                    InterNavHost(navController = navController)
                }
            } else {
                // app rail en row
                // le paso la funcion que debe hacer cuando clickeo, como en el ejemplo del profe
                Scaffold(
                    topBar = { TopInterlinkBar() }
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        //NavigationInterRail()
                        InterNavHost(navController = navController)
                    }
                }
            }
        }


    }

}





@Preview
@Composable
fun InterlinkAppPreview(){

    InterlinkApp()
}

@Preview(device = Devices.TABLET)
@Composable
fun InterlinkAppPreview2(){
    InterlinkApp()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun scaffoldShow(){
    InterlinkTheme{
        Scaffold(
            topBar = { TopInterlinkBar() },
            containerColor = md_theme_light_background
        ) {

        }
    }
}