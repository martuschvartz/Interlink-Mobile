package com.example.interlink.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.interlink.ui.components.BottomInterBar
import com.example.interlink.ui.components.TopInterlinkBar
import com.example.interlink.ui.navigation.InterNavHost
import com.example.interlink.ui.theme.InterlinkTheme

// dejarle a bottom bar y riel por el divider, como la top bar

// usar box with constraints para tema responsive

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
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },

                    ) {innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        InterNavHost(navController = navController)
                    }
                }
            } else {
                // app rail en row
                // le paso la funcion que debe hacer cuando clickeo, como en el ejemplo del profe
                Scaffold(
                    topBar = { TopInterlinkBar() }
                ) {innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
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

/*
@Preview(device = Devices.TABLET)
@Composable
fun InterlinkAppPreview2(){
    InterlinkApp()
}*/