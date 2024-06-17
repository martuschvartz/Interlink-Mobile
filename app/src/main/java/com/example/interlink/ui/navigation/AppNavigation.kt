package com.example.interlink.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.interlink.R

enum class AppNavigation(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Default.Home, "home"),
    ACTIVITY(R.string.activity, Icons.Filled.NotificationsActive, "activity"),
    DEVICES(R.string.devices, Icons.Filled.Devices, "devices")
}