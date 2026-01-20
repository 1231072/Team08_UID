package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ScheduleStatItem(
    val count: String,
    val label: String,
    val color: Color
)

enum class TaskType {
    MEDICATION, APPOINTMENT, OTHER
}

data class ScheduleTaskItem(
    val id: Long,
    val title: String,
    val time: String,
    val person: String,
    val type: TaskType,
    val icon: ImageVector,
    val isCompleted: Boolean = false,
    val isUrgent: Boolean = false
) {
    val timeAndPerson: String
        get() = "$time • $person"
}