package com.example.uid_finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uid_finalproject.MainViewModel
import com.example.uid_finalproject.model.QuickActionItem
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    // mocked data

    val rooms = viewModel.rooms
    val statusItems = viewModel.statusItems
    val activities = viewModel.activities

    // Podemos usar o snackbar para dar feedback visual na apresentação
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // NOVOS DADOS: Ações Rápidas
    val quickActions = listOf(
        QuickActionItem("Lock All", Icons.Outlined.Security, Color(0xFF1976D2), Color(0xFFE3F2FD)),
        QuickActionItem("Night Mode", Icons.Outlined.Lightbulb, Color(0xFFFBC02D), Color(0xFFFFF9C4)),
        QuickActionItem("Eco Mode", Icons.Outlined.Bolt, Color(0xFF7B1FA2), Color(0xFFF3E5F5)),
        QuickActionItem("Reminders", Icons.Outlined.Notifications, Color(0xFF388E3C), Color(0xFFE8F5E9))
    )
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Mic, contentDescription = "Voice") }
                    IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") }
                }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(
                navController = navController,
                currentRoute = Routes.HOME
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // alert
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }
            item { SectionHeader("Home Status") }

            // status grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatusCard(statusItems[0], Modifier.weight(1f))
                        StatusCard(statusItems[1], Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatusCard(statusItems[2], Modifier.weight(1f))
                        StatusCard(statusItems[3], Modifier.weight(1f))
                    }
                }
            }

            // room
            item { SectionHeader("Rooms") }

            items(rooms) { room ->
                RoomListItem(
                    room = room,
                    onTemperatureChange = { newTemperature ->
                        viewModel.updateRoomTemperature(room.name, newTemperature)
                    },
                    onLightIntensityChange = { newIntensity ->
                        viewModel.updateRoomLightIntensity(room.name, newIntensity)
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader("Recent Activity")
            }

            // activity list
            val itemsToShow = if (viewModel.showAllActivities) activities else activities.take(4)
            items(itemsToShow) { activity ->
                ActivityRow(activity)
            }

            if (activities.size > 4) {
                item {
                    TextButton(onClick = { viewModel.onSeeMoreActivitiesClick() }) {
                        Text(if (viewModel.showAllActivities) "See Less" else "See More")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader("Quick Actions")
            }

            // quick actions
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        QuickActionCard(quickActions[0], modifier = Modifier.weight(1f), onClick = { viewModel.lockAllDoors() })
                        QuickActionCard(
                            item = quickActions[1],
                            modifier = Modifier.weight(1f),
                            isActive = viewModel.isNightModeActive,
                            onClick = {
                                viewModel.toggleNightMode()
                                scope.launch {
                                    val msg = if (viewModel.isNightModeActive)
                                        "Night Mode Activated: Lights off & Doors locked"
                                    else "Night Mode Deactivated"
                                    snackbarHostState.showSnackbar(msg)
                                }
                            }
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        QuickActionCard(quickActions[2], modifier = Modifier.weight(1f), onClick = {})
                        QuickActionCard(quickActions[3], modifier = Modifier.weight(1f), onClick = { navController.navigate(Routes.SCHEDULE) })
                    }
                }
            }


            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}