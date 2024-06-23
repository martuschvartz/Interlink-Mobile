package com.example.interlink.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.devices.StoredEventEntryViewModel
import com.example.interlink.ui.pages.ActivityPage
import com.example.interlink.ui.pages.DevicesPage
import com.example.interlink.ui.pages.HomePage

@Composable
fun InterNavHost(
    navController: NavHostController,
    startDestination: String = AppNavigation.HOME.route,
    favDevViewModel : FavoritesEntryViewModel?,
    storedEventViewModel : StoredEventEntryViewModel?
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = AppNavigation.HOME.route){
            if (favDevViewModel != null) {
                HomePage(favDevViewModel = favDevViewModel)
            }
        }

        composable(route = AppNavigation.ACTIVITY.route){
            if (storedEventViewModel != null) {
                ActivityPage(storedEvents = storedEventViewModel)
            }
        }

        composable(route = AppNavigation.DEVICES.route){
            if (favDevViewModel != null) {
                DevicesPage(favDevViewModel = favDevViewModel)
            }
        }
    }
}