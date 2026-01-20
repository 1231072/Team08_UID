package com.example.uid_finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.uid_finalproject.ui.screens.DashboardScreen
import androidx.navigation.compose.NavHost
import com.example.uid_finalproject.ui.navigation.Routes
import androidx.navigation.compose.composable
import com.example.uid_finalproject.ui.screens.SecurityScreen
import com.example.uid_finalproject.ui.screens.ScheduleScreen
import com.example.uid_finalproject.ui.screens.EnergyScreen
import com.example.uid_finalproject.ui.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val viewModel: MainViewModel by viewModels()
        
        setContent {
            val useDarkTheme = viewModel.isDarkTheme || isSystemInDarkTheme()
            
            MaterialTheme(
                colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME
                    ) {
                        composable(Routes.HOME) {
                            DashboardScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(Routes.SECURITY) {
                            SecurityScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Routes.SCHEDULE) {
                            ScheduleScreen(navController = navController)
                        }
                        composable(Routes.ENERGY) {
                            EnergyScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Routes.SETTINGS) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}