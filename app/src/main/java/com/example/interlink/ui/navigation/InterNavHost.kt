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
    useLazyColumn: Boolean,
    favDevViewModel: FavoritesEntryViewModel?,
    storedEventViewModel : StoredEventEntryViewModel?,
    isPhone: Boolean
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = AppNavigation.HOME.route){
            if (favDevViewModel != null) {
                if (storedEventViewModel != null) {
                    HomePage(
                        favDevViewModel = favDevViewModel,
                        storedEvents = storedEventViewModel,
                        useLazyColumn = useLazyColumn
                    )
                }
            }
        }

        composable(route = AppNavigation.ACTIVITY.route){
            if (storedEventViewModel != null) {
                ActivityPage(
                    storedEvents = storedEventViewModel,
                    useLazyColumn = useLazyColumn,
                    isPhone = isPhone
                )
            }
        }

        composable(route = AppNavigation.DEVICES.route){
            if (favDevViewModel != null) {
                DevicesPage(
                    favDevViewModel = favDevViewModel,
                    useLazyColumn = useLazyColumn,
                    isPhone = isPhone
                )
            }
        }
    }
}