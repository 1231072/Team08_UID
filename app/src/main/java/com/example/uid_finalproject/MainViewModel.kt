package com.example.uid_finalproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.uid_finalproject.model.*

class MainViewModel : ViewModel() {
    val rooms = mutableStateListOf(
        RoomItem("Kids Room", "22°C • Light: 60%", 0, false, 22, areDoorsClosed = false, areWindowsClosed = false),
        RoomItem("Master Bedroom", "20°C • Light: Off", 0, true, 20, areDoorsClosed = true, areWindowsClosed = true),
        RoomItem("Teen Room", "23°C • Light: 80%", 0, true, 23, areDoorsClosed = false, areWindowsClosed = false),
        RoomItem("Grandparents Room", "24°C • Light: 40%", 0, true, 24, areDoorsClosed = true, areWindowsClosed = true),
        RoomItem("Living Room", "22°C • Light: 100%", 0, true, 22, areDoorsClosed = false, areWindowsClosed = false),
    )

    val statusItems = mutableStateListOf(
        HomeStatusItem("Security", "Active", Icons.Outlined.Security, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
        HomeStatusItem("Temperature", "22°C", Icons.Outlined.Thermostat, Color(0xFFE3F2FD), Color(0xFF1565C0)),
        HomeStatusItem("Lights On", "5 of 12", Icons.Outlined.Lightbulb, Color(0xFFFFFDE7), Color(0xFFF9A825)),
        HomeStatusItem("Energy", "Normal", Icons.Outlined.Bolt, Color(0xFFF3E5F5), Color(0xFF6A1B9A))
    )

    val activities = mutableStateListOf(
        RecentActivityItem("Window opened in Kids Room", "2 minutes ago", Icons.Default.Notifications, Color(0xFFFFEBEE), Color(0xFFD32F2F)),
        RecentActivityItem("Temperature adjusted in Living Room", "15 minutes ago", Icons.Default.Thermostat, Color(0xFFE3F2FD), Color(0xFF1976D2)),
        RecentActivityItem("Grandma took medication", "1 hour ago", Icons.Default.Person, Color(0xFFE8F5E9), Color(0xFF388E3C))
    )
    
    val entryPoints = mutableStateListOf(
        EntryPointItem(1, "Window", EntryState.CURRENTLY_OPEN, Icons.Outlined.Window, isKidsRoom = true),
        EntryPointItem(2, "Door", EntryState.CLOSED, Icons.Outlined.DoorFront, isKidsRoom = true),

        EntryPointItem(10, "Front Door", EntryState.LOCKED, Icons.Outlined.DoorFront),
        EntryPointItem(11, "Back Door", EntryState.LOCKED, Icons.Outlined.DoorFront),
        EntryPointItem(12, "Master Bedroom Window", EntryState.CLOSED, Icons.Outlined.Window),
        EntryPointItem(13, "Living Room Window", EntryState.CLOSED, Icons.Outlined.Window),
        EntryPointItem(14, "Grandparents Window", EntryState.CLOSED, Icons.Outlined.Window)
    )

    var showCameraPopup by mutableStateOf(false)

    fun toggleEntryPoint(id: Int) {
        val index = entryPoints.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = entryPoints[index]
            val newState = when (item.state) {
                EntryState.LOCKED, EntryState.CLOSED ->
                    if (item.name.contains("Window")) EntryState.CURRENTLY_OPEN else EntryState.OPEN
                else ->
                    if (item.name.contains("Window")) EntryState.CLOSED else EntryState.LOCKED
            }
            entryPoints[index] = item.copy(state = newState)
        }
    }

    var isNightModeActive by mutableStateOf(false)
        private set

    fun toggleNightMode() {
        isNightModeActive = !isNightModeActive
        if (isNightModeActive) {
            rooms.forEach {
                it.areDoorsClosed = true
                it.areWindowsClosed = true
                it.temperature = 20
            }
            entryPoints.forEachIndexed { index, item ->
                val newState = if (item.name.contains("Window")) EntryState.CLOSED else EntryState.LOCKED
                entryPoints[index] = item.copy(state = newState)
            }
            statusItems[1] = statusItems[1].copy(value = "20°C")
            activities.add(0, RecentActivityItem("Night Mode Activated", "Just now", Icons.Outlined.Lightbulb, Color(0xFFFFF9C4), Color(0xFFFBC02D)))
        } else {
            // Optionally, revert the changes when night mode is deactivated
            statusItems[1] = statusItems[1].copy(value = "22°C") // Assuming default is 22
            activities.add(0, RecentActivityItem("Night Mode Deactivated", "Just now", Icons.Outlined.Lightbulb, Color(0xFFE0E0E0), Color(0xFF000000)))
        }
    }
}