package com.example.interlink.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.interlink.database.FavoritesDatabase
import com.example.interlink.ui.components.BottomInterBar
import com.example.interlink.ui.components.TopInterlinkBar
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.devices.FavoritesEntryViewModelFactory
import com.example.interlink.ui.devices.StoredEventEntryViewModel
import com.example.interlink.ui.devices.StoredEventEntryViewModelFactory
import com.example.interlink.ui.navigation.InterNavHost
import com.example.interlink.ui.navigation.NavigationInterRail
import com.example.interlink.ui.theme.InterlinkTheme


@Composable
fun InterlinkApp() {
    InterlinkTheme {

        val context = LocalContext.current

        // Controladores de Navegación
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        // Base de datos para devices favoritos
        val database = remember { FavoritesDatabase.getDatabase(context) }
        val viewModelFactory = remember { FavoritesEntryViewModelFactory(database.favoriteDeviceDao()) }
        val favDevViewModel : FavoritesEntryViewModel = viewModel(factory = viewModelFactory)

        // Base de datos para eventos
        val eventViewModelFactory = remember { StoredEventEntryViewModelFactory(database.storedEventDao()) }
        val storedEventViewModel : StoredEventEntryViewModel = viewModel(factory = eventViewModelFactory)

        BoxWithConstraints{
            // Para el caso del celular, en portrait o landscape
            if (maxWidth < 600.dp ) {

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
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                    {
                        InterNavHost(navController = navController, favDevViewModel = favDevViewModel, storedEventViewModel = storedEventViewModel)
                    }
                }

            // Para el caso de la tablet, portrait o landscape
            } else {

                Scaffold(
                    topBar = { TopInterlinkBar() }
                ) {innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavigationInterRail(
                            currentDestination = currentDestination
                        ){
                                route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        InterNavHost(navController = navController, favDevViewModel = favDevViewModel, storedEventViewModel = storedEventViewModel)
                    }
                }
            }
        }


    }

}





@Preview(locale = "es")
@Composable
fun InterlinkAppPreview(){
    InterlinkApp()
}


@Preview(device = Devices.TABLET)
@Composable
fun InterlinkAppPreview2(){
    InterlinkApp()
}