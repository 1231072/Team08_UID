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
    val info: String,
    val iconRes: Int,
    val isOnline: Boolean
)

data class ActivityItem(
    val title: String,
    val time: String,
    val type: ActivityType
)

enum class ActivityType { ALERT, INFO, GENERIC }

data class RecentActivityItem(
    val title: String,
    val time: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconColor: Color
)

data class QuickActionItem(
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val bgColor: Color
)