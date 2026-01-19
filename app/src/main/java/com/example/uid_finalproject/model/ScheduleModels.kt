package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ScheduleStatItem(
    val count: String,
    val label: String,
    val color: Color
)

data class ScheduleTaskItem(
    val title: String,
    val timeAndPerson: String,
    val icon: ImageVector,
    val isCompleted: Boolean = false,
    val isUrgent: Boolean = false
)