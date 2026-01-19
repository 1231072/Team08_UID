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
import com.example.uid_finalproject.model.*
import com.example.uid_finalproject.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen() {

    val securityStatusItems = listOf(
        SecurityStatusCount("Doors Locked", "2 of 2", Icons.Outlined.Lock, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
        SecurityStatusCount("Windows Open", "1 of 4", Icons.Outlined.Window, Color(0xFFFFEBEE), Color(0xFFD32F2F))
    )
    val kidsRoomItems = listOf(
        EntryPointItem("Window", EntryState.CURRENTLY_OPEN, Icons.Outlined.Window),
        EntryPointItem("Door", EntryState.CLOSED, Icons.Outlined.DoorFront)
    )
    val kidsMotion = MotionSensorItem("Motion Sensor", "Activity detected 5 min ago", true)

    val allEntryPoints = listOf(
        EntryPointItem("Front Door", EntryState.LOCKED, Icons.Outlined.DoorFront),
        EntryPointItem("Back Door", EntryState.LOCKED, Icons.Outlined.DoorFront),
        EntryPointItem("Master Bedroom Window", EntryState.CLOSED, Icons.Outlined.Window),
        EntryPointItem("Living Room Window", EntryState.CLOSED, Icons.Outlined.Window),
        EntryPointItem("Grandparents Window", EntryState.CLOSED, Icons.Outlined.Window)
    )

    val motionSensors = listOf(
        MotionSensorItem("Living Room", "No motion detected", true),
        MotionSensorItem("Hallway", "Active", true)
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Home, "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Security, "Security") }, label = { Text("Security") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.CalendarToday, "Schedule") }, label = { Text("Schedule") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Bolt, "Energy") }, label = { Text("Energy") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, "Settings") }, label = { Text("Settings") })
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // alert
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }
            item { SectionHeader("Security Status") }
            item {
                SecurityAlertCard(
                    title = "1 Alert",
                    message = "Kids room window open",
                    icon = Icons.Default.Warning
                )
            }

            // status grid
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecurityStatusSquare(securityStatusItems[0], Modifier.weight(1f))
                    SecurityStatusSquare(securityStatusItems[1], Modifier.weight(1f))
                }
            }

            item { SectionHeader("Kids Room Safety") }

            items(kidsRoomItems) { item ->
                EntryPointRow(item)
            }
            item { MotionSensorRow(kidsMotion) }

            item {
                LargeActionButton(
                    text = "View Live Camera",
                    icon = Icons.Default.Videocam,
                    backgroundColor = Color(0xFF2962FF),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // entry points
            item { SectionHeader("All Entry Points") }
            items(allEntryPoints) { item ->
                EntryPointRow(item)
            }

            // motion
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
                        modifier = Modifier.weight(1f)
                    )
                    LargeActionButton(
                        text = "View All Cameras",
                        icon = Icons.Default.Videocam,
                        backgroundColor = Color(0xFF2962FF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}