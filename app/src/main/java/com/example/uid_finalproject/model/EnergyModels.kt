package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class WeeklyBarData(
    val day: String,
    val value: Float,
    val isSelected: Boolean = false
)

data class RoomEnergyItem(
    val name: String,
    val kwh: String,
    val percent: Float,
    val color: Color
)

data class EnergyConsumerItem(
    val title: String,
    val kwh: String,
    val percent: String,
    val icon: ImageVector,
    val color: Color,
    val iconColor: Color
)

data class EnergyAutomationItem(
    val title: String,
    val subtitle: String,
    val isEnabled: Boolean
)