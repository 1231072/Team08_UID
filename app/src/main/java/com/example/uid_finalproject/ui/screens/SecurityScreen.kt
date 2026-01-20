package com.example.uid_finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uid_finalproject.MainViewModel
import com.example.uid_finalproject.model.*
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val entryPoints = viewModel.entryPoints
    val kidsRoomItems = entryPoints.filter { it.isKidsRoom }
    val otherEntryPoints = entryPoints.filter { !it.isKidsRoom }

    val doorsLockedCount = entryPoints.count { it.name.contains("Door") && (it.state == EntryState.LOCKED || it.state == EntryState.CLOSED) }
    val totalDoors = entryPoints.count { it.name.contains("Door") }
    val windowsOpenCount = entryPoints.count { it.name.contains("Window") && (it.state == EntryState.OPEN || it.state == EntryState.CURRENTLY_OPEN) }
    val totalWindows = entryPoints.count { it.name.contains("Window") }

    val doorsLockedStr = "$doorsLockedCount of $totalDoors"
    val windowsOpenStr = "$windowsOpenCount of $totalWindows"

    val securityStatusItems = listOf(
        SecurityStatusCount("Doors Locked", doorsLockedStr, Icons.Outlined.Lock, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
        SecurityStatusCount("Windows Open", windowsOpenStr, Icons.Outlined.Window, Color(0xFFFFEBEE), Color(0xFFD32F2F))
    )

    val kidsMotion = MotionSensorItem("Motion Sensor", "Activity detected 5 min ago", true)
    val motionSensors = listOf(
        MotionSensorItem("Living Room", "No motion detected", true),
        MotionSensorItem("Hallway", "Active", true)
    )

    if (viewModel.showCameraPopup) {
        CameraDialog(onDismiss = { viewModel.showCameraPopup = false })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") } }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(navController = navController, currentRoute = Routes.SECURITY)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val kidsRoomAlerts = entryPoints.count { it.isKidsRoom && (it.state == EntryState.OPEN || it.state == EntryState.CURRENTLY_OPEN) }
            val hasKidsRoomAlert = kidsRoomAlerts > 0

            if (hasKidsRoomAlert) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    AlertBanner("Alert: Window/Door Open in Kids Room")
                }
            }

            item { SectionHeader("Security Status") }

            item {
                if (hasKidsRoomAlert) {
                    SecurityAlertCard(
                        title = "$kidsRoomAlerts Alert(s)",
                        message = "Kids room entry point open",
                        icon = Icons.Default.Warning
                    )
                } else {
                    SecuritySafeCard()
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecurityStatusSquare(securityStatusItems[0], Modifier.weight(1f))
                    SecurityStatusSquare(securityStatusItems[1], Modifier.weight(1f))
                }
            }

            item { SectionHeader("Kids Room Safety") }

            items(kidsRoomItems) { item ->
                EntryPointRow(
                    item = item,
                    onStatusClick = { viewModel.toggleEntryPoint(item.id) }
                )
            }
            item { MotionSensorRow(kidsMotion) }

            item {
                LargeActionButton(
                    text = "View Live Camera",
                    icon = Icons.Default.Videocam,
                    backgroundColor = Color(0xFF2962FF),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.showCameraPopup = true }
                )
            }

            item { SectionHeader("All Entry Points") }
            items(otherEntryPoints) { item ->
                EntryPointRow(
                    item = item,
                    onStatusClick = { viewModel.toggleEntryPoint(item.id) }
                )
            }

            item { SectionHeader("Motion Detection") }
            items(motionSensors) { item ->
                MotionSensorRow(item)
            }

            item {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LargeActionButton(
                        text = "Lock All Doors",
                        icon = Icons.Default.Lock,
                        backgroundColor = Color(0xFFD32F2F),
                        modifier = Modifier.weight(1f),
                        onClick = { 
                            entryPoints.forEachIndexed { index, item ->
                                val newState = if (item.name.contains("Window")) EntryState.CLOSED else EntryState.LOCKED
                                entryPoints[index] = item.copy(state = newState)
                            }
                        }
                    )
                    LargeActionButton(
                        text = "View All Cameras",
                        icon = Icons.Default.Videocam,
                        backgroundColor = Color(0xFF2962FF),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.showCameraPopup = true }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}