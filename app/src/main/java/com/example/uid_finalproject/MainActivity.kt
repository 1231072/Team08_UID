package com.example.uid_finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.uid_finalproject.ui.screens.DashboardScreen
import androidx.navigation.compose.NavHost
import com.example.uid_finalproject.ui.navigation.Routes
import androidx.navigation.compose.composable
import com.example.uid_finalproject.ui.screens.SecurityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
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
                            DashboardScreen(navController = navController)
                        }

                        composable(Routes.SECURITY) {
                            SecurityScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}