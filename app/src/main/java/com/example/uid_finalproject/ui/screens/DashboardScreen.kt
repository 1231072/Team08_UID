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
import com.example.uid_finalproject.model.HomeStatusItem
import com.example.uid_finalproject.model.RoomItem
import com.example.uid_finalproject.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    // mocked data
    val statusItems = listOf(
        HomeStatusItem("Security", "Active", Icons.Outlined.Security, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
        HomeStatusItem("Temperature", "22°C", Icons.Outlined.Thermostat, Color(0xFFE3F2FD), Color(0xFF1565C0)),
        HomeStatusItem("Lights On", "5 of 12", Icons.Outlined.Lightbulb, Color(0xFFFFFDE7), Color(0xFFF9A825)),
        HomeStatusItem("Energy", "Normal", Icons.Outlined.Bolt, Color(0xFFF3E5F5), Color(0xFF6A1B9A))
    )

    val rooms = listOf(
        RoomItem("Kids Room", "22°C • Light: 60%", 0, false),
        RoomItem("Master Bedroom", "20°C • Light: Off", 0, true),
        RoomItem("Teen Room", "23°C • Light: 80%", 0, true),
        RoomItem("Grandparents Room", "24°C • Light: 40%", 0, true),
        RoomItem("Living Room", "22°C • Light: 100%", 0, true),
    )

    Scaffold(
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
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Security, "Security") }, label = { Text("Security") })
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
                RoomListItem(room)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}