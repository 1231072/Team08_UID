package com.example.uid_finalproject.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uid_finalproject.model.*

class SecurityViewModel : ViewModel() {

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



    fun lockAllDoors() {
        entryPoints.forEachIndexed { index, item ->
            val newState = if (item.name.contains("Window")) EntryState.CLOSED else EntryState.LOCKED
            entryPoints[index] = item.copy(state = newState)
        }
    }

    val doorsLockedCount: Int
        get() = entryPoints.count { it.name.contains("Door") && (it.state == EntryState.LOCKED || it.state == EntryState.CLOSED) }

    val totalDoors: Int
        get() = entryPoints.count { it.name.contains("Door") }

    val windowsOpenCount: Int
        get() = entryPoints.count { it.name.contains("Window") && (it.state == EntryState.OPEN || it.state == EntryState.CURRENTLY_OPEN) }

    val totalWindows: Int
        get() = entryPoints.count { it.name.contains("Window") }

    val kidsRoomAlerts: Int
        get() = entryPoints.count { it.isKidsRoom && (it.state == EntryState.OPEN || it.state == EntryState.CURRENTLY_OPEN) }

    val hasKidsRoomAlert: Boolean
        get() = kidsRoomAlerts > 0
}