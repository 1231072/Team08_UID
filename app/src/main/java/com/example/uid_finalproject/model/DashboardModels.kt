package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeStatusItem(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color,
    val textColor: Color
)

data class RoomItem(
    val name: String,
    val info: String, // ex: "22°C • Light: 60%"
    val iconRes: Int, // Poderia ser um Drawable ou ImageVector
    val isOnline: Boolean
)

data class ActivityItem(
    val title: String,
    val time: String,
    val type: ActivityType
)

enum class ActivityType { ALERT, INFO, GENERIC }

data class QuickActionItem(
    val label: String,
    val icon: ImageVector,
    val color: Color
)