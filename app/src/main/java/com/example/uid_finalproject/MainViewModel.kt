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
import androidx.lifecycle.viewModelScope
import com.example.uid_finalproject.data.ApiService
import com.example.uid_finalproject.data.NewUser
import com.example.uid_finalproject.data.SensorData
import com.example.uid_finalproject.model.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = ApiService.create()

    val rooms = mutableStateListOf(
        RoomItem("Kids Room", 0, false, 22, 60, areDoorsClosed = false, areWindowsClosed = false),
        RoomItem("Master Bedroom", 0, true, 20, 0, areDoorsClosed = true, areWindowsClosed = true),
        RoomItem("Teen Room", 0, true, 23, 80, areDoorsClosed = false, areWindowsClosed = false),
        RoomItem("Grandparents Room", 0, true, 24, 40, areDoorsClosed = true, areWindowsClosed = true),
        RoomItem("Living Room", 0, true, 22, 100, areDoorsClosed = false, areWindowsClosed = false),
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

    val familyMembers = mutableStateListOf(
        FamilyMember("Emma (5)", "👧"),
        FamilyMember("Alex (14)", "👦"),
        FamilyMember("Dad", "👨"),
        FamilyMember("Mom", "👩")
    )

    val notificationSettings = mutableStateListOf(
        SettingToggleItem("Security Alerts", "Doors, windows, motion", true),
        SettingToggleItem("Medication Reminders", "For family members", true),
        SettingToggleItem("Energy Reports", "Daily consumption", false),
        SettingToggleItem("System Updates", "New features", true)
    )

    val accessibilitySettings = mutableStateListOf(
        SettingToggleItem("Large Text", "Easier to read", false),
        SettingToggleItem("High Contrast", "Better visibility", false),
        SettingToggleItem("Voice Assistant", "Audio guidance", true)
    )

    var fontSize by mutableStateOf(1f)
    var systemVolume by mutableStateOf(0.75f)
    var isDarkTheme by mutableStateOf(false)

    var showCameraPopup by mutableStateOf(false)
    var showAllActivities by mutableStateOf(false)
    var energyFilter by mutableStateOf("Real-Time")
    var loggedInUser by mutableStateOf("David Costa")
    var userEmail by mutableStateOf("david.costa@email.com")

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            try {
                val user = apiService.getUser()
                loggedInUser = user.name
                userEmail = user.email
            } catch (e: Exception) {
            }
        }
    }

    fun addFamilyMember(name: String, emoji: String) {
        viewModelScope.launch {
            try {
                val newUser = NewUser(name, "")
                val response = apiService.addUser(newUser)
                if (response.isSuccessful && response.body() != null) {
                    familyMembers.add(FamilyMember(name, emoji))
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateRoomTemperature(roomName: String, newTemperature: Int) {
        viewModelScope.launch {
            try {
                val sensorData = SensorData("temperature", newTemperature.toString(), "°C")
                apiService.postSensorData(sensorData)

                val roomIndex = rooms.indexOfFirst { it.name == roomName }
                if (roomIndex != -1) {
                    rooms[roomIndex] = rooms[roomIndex].copy(temperature = newTemperature)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateRoomLightIntensity(roomName: String, newIntensity: Int) {
        viewModelScope.launch {
            try {
                val sensorData = SensorData("light", newIntensity.toString(), "%")
                apiService.postSensorData(sensorData)

                val roomIndex = rooms.indexOfFirst { it.name == roomName }
                if (roomIndex != -1) {
                    rooms[roomIndex] = rooms[roomIndex].copy(lightIntensity = newIntensity)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun onSeeMoreActivitiesClick() {
        showAllActivities = !showAllActivities
    }

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
            statusItems[1] = statusItems[1].copy(value = "22°C")
            activities.add(0, RecentActivityItem("Night Mode Deactivated", "Just now", Icons.Outlined.Lightbulb, Color(0xFFE0E0E0), Color(0xFF000000)))
        }
    }

    fun lockAllDoors() {
        entryPoints.forEachIndexed { index, item ->
            val newState = if (item.name.contains("Window")) EntryState.CLOSED else EntryState.LOCKED
            entryPoints[index] = item.copy(state = newState)
        }
    }

    fun logout() {
        isNightModeActive = false
    }

    fun onNotificationSettingChanged(item: SettingToggleItem, isChecked: Boolean) {
        val index = notificationSettings.indexOf(item)
        if (index != -1) {
            notificationSettings[index] = item.copy(isChecked = isChecked)
        }
    }

    fun onAccessibilitySettingChanged(item: SettingToggleItem, isChecked: Boolean) {
        val index = accessibilitySettings.indexOf(item)
        if (index != -1) {
            accessibilitySettings[index] = item.copy(isChecked = isChecked)
        }
    }

    fun onThemeChanged(dark: Boolean) {
        isDarkTheme = dark
    }
}