package com.example.uid_finalproject.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.uid_finalproject.ui.navigation.Routes

@Composable
fun SmartHomeBottomBar(
    navController: NavController,
    currentRoute: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.HOME,
            onClick = {
                if (currentRoute != Routes.HOME) {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SECURITY,
            onClick = {
                if (currentRoute != Routes.SECURITY) {
                    navController.navigate(Routes.SECURITY) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Security, "Security") },
            label = { Text("Security") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SCHEDULE,
            onClick = {
                if (currentRoute != Routes.SCHEDULE) {
                    navController.navigate(Routes.SCHEDULE) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.CalendarToday, "Schedule") },
            label = { Text("Schedule") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.ENERGY,
            onClick = {
                if (currentRoute != Routes.ENERGY) {
                    navController.navigate(Routes.ENERGY) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Bolt, "Energy") },
            label = { Text("Energy") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.SETTINGS,
            onClick = {
                if (currentRoute != Routes.SETTINGS) {
                    navController.navigate(Routes.SETTINGS) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Settings, "Settings") },
            label = { Text("Settings") }
        )
    }
}