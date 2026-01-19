package com.example.uid_finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uid_finalproject.model.ScheduleStatItem
import com.example.uid_finalproject.model.ScheduleTaskItem
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController) {

    // mocked data
    val stats = listOf(
        ScheduleStatItem("3", "Medications", Color(0xFFE3F2FD)),
        ScheduleStatItem("1", "Appointments", Color(0xFFF3E5F5)),
        ScheduleStatItem("1", "Completed", Color(0xFFE8F5E9))
    )

    val urgentTasks = listOf(
        ScheduleTaskItem("Heart Medication", "14:00 • Grandpa", Icons.Default.Favorite, isUrgent = true),
        ScheduleTaskItem("Evening Medication", "20:00 • Grandma", Icons.Default.Favorite, isUrgent = true)
    )

    val upcomingTasks = listOf(
        ScheduleTaskItem("Heart Medication", "14:00 • Grandpa", Icons.Default.Favorite),
        ScheduleTaskItem("Evening Medication", "20:00 • Grandma", Icons.Default.Favorite),
        ScheduleTaskItem("Doctor Appointment", "10:30 • Grandpa", Icons.Default.Schedule),
        ScheduleTaskItem("Kids Homework Time", "16:00 • Kids", Icons.Default.Notifications)
    )

    val completedTasks = listOf(
        ScheduleTaskItem("Blood Pressure Medication", "08:00 • Grandma", Icons.Default.Favorite, isCompleted = true)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schedule", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") }
                }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(
                navController = navController,
                currentRoute = Routes.SCHEDULE
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }

            item { SectionHeader("Today's Schedule") }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScheduleStatCard(stats[0], Modifier.weight(1f))
                    ScheduleStatCard(stats[1], Modifier.weight(1f))
                    ScheduleStatCard(stats[2], Modifier.weight(1f))
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFD32F2F))
                            Text(
                                text = "Medication Reminders",
                                color = Color(0xFFD32F2F),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        urgentTasks.forEach { task ->
                            ScheduleTaskRow(task)
                        }
                    }
                }
            }

            item {
                SectionHeaderWithAction("Upcoming") {
                }
            }
            items(upcomingTasks) { task ->
                ScheduleTaskRow(task)
            }

            item {
                Row(
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4CAF50))
                    Text(
                        text = "Completed Today",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            items(completedTasks) { task ->
                ScheduleTaskRow(task)
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}