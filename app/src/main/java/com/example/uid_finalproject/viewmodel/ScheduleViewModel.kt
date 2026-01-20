package com.example.uid_finalproject.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uid_finalproject.model.*

class ScheduleViewModel : ViewModel() {

    val tasks = mutableStateListOf(
        ScheduleTaskItem(1, "Heart Medication", "14:00", "Grandpa", TaskType.MEDICATION, Icons.Default.Favorite, isUrgent = true),
        ScheduleTaskItem(2, "Evening Medication", "20:00", "Grandma", TaskType.MEDICATION, Icons.Default.Favorite, isUrgent = true),
        ScheduleTaskItem(3, "Doctor Appointment", "10:30", "Grandpa", TaskType.APPOINTMENT, Icons.Default.Schedule),
        ScheduleTaskItem(4, "Kids Homework Time", "16:00", "Kids", TaskType.OTHER, Icons.Default.Notifications),
        ScheduleTaskItem(5, "Blood Pressure Meds", "08:00", "Grandma", TaskType.MEDICATION, Icons.Default.Favorite, isCompleted = true)
    )

    var showAddDialog by mutableStateOf(false)

    fun addTask(title: String, time: String, person: String, isMedication: Boolean) {
        val newTask = ScheduleTaskItem(
            id = System.currentTimeMillis(),
            title = title,
            time = time,
            person = person,
            type = if (isMedication) TaskType.MEDICATION else TaskType.OTHER,
            icon = if (isMedication) Icons.Default.Favorite else Icons.Default.Event,
            isUrgent = isMedication
        )
        tasks.add(newTask)
    }

    fun toggleTask(id: Long) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) {
            val task = tasks[index]
            tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }


    val medicationCount: Int
        get() = tasks.count { !it.isCompleted && it.type == TaskType.MEDICATION }

    val appointmentCount: Int
        get() = tasks.count { !it.isCompleted && it.type == TaskType.APPOINTMENT }

    val completedCount: Int
        get() = tasks.count { it.isCompleted }
}