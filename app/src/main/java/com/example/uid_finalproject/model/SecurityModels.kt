package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class SecurityStatusCount(
    val title: String,
    val count: String,
    val icon: ImageVector,
    val color: Color,
    val contentColor: Color
)


data class EntryPointItem(
    val id: Int,
    val name: String,
    val state: EntryState,
    val icon: ImageVector,
    val isKidsRoom: Boolean = false
)

enum class EntryState(val label: String, val color: Color, val textColor: Color) {
    LOCKED("Locked", Color(0xFF4CAF50), Color.White),
    CLOSED("Closed", Color(0xFF4CAF50), Color.White),
    OPEN("Open", Color(0xFFD32F2F), Color.White),
    CURRENTLY_OPEN("Currently open", Color(0xFFFFEBEE), Color(0xFFD32F2F)) // Texto vermelho fundo rosa (ex: alerta)
}


data class MotionSensorItem(
    val location: String,
    val statusText: String,
    val isActive: Boolean
)