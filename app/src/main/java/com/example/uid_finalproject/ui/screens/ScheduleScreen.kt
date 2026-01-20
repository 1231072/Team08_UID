package com.example.uid_finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uid_finalproject.model.ScheduleStatItem
import com.example.uid_finalproject.model.TaskType
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes
import com.example.uid_finalproject.viewmodel.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    // Buscar lista completa
    val allTasks = viewModel.tasks

    // 1. Filtro: Secção Vermelha (Apenas Meds não completados)
    val urgentMedications = allTasks.filter {
        it.type == TaskType.MEDICATION && !it.isCompleted
    }

    // 2. Filtro: Upcoming (Tudo o que não está completado)
    // O requisito diz "Every medication should be both on the upcoming and Medication Reminders"
    // Portanto, Upcoming mostra TUDO o que falta fazer.
    val upcomingTasks = allTasks.filter { !it.isCompleted }

    // 3. Filtro: Completados Hoje
    val completedTasks = allTasks.filter { it.isCompleted }

    // Stats Dinâmicos
    val stats = listOf(
        ScheduleStatItem(viewModel.medicationCount.toString(), "Medications", Color(0xFFE3F2FD)),
        ScheduleStatItem(viewModel.appointmentCount.toString(), "Appointments", Color(0xFFF3E5F5)),
        ScheduleStatItem(viewModel.completedCount.toString(), "Completed", Color(0xFFE8F5E9))
    )

    // Mostrar Popup se necessário
    if (viewModel.showAddDialog) {
        AddTaskDialog(
            onDismiss = { viewModel.showAddDialog = false },
            onAdd = { title, time, person, isMed ->
                viewModel.addTask(title, time, person, isMed)
                viewModel.showAddDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schedule", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") } }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(navController = navController, currentRoute = Routes.SCHEDULE)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ... (Alert Banner igual)
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }

            // Stats Row Dinâmica
            item {
                SectionHeader("Today's Schedule")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScheduleStatCard(stats[0], Modifier.weight(1f))
                    ScheduleStatCard(stats[1], Modifier.weight(1f))
                    ScheduleStatCard(stats[2], Modifier.weight(1f))
                }
            }

            // Secção Vermelha (Medications)
            if (urgentMedications.isNotEmpty()) {
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
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, // <--- ADICIONA "fontWeight ="
                                    modifier = Modifier.padding(start = 8.dp)
                                )                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            urgentMedications.forEach { task ->
                                ScheduleTaskRow(
                                    item = task,
                                    onCheckClick = { viewModel.toggleTask(task.id) }
                                )
                            }
                        }
                    }
                }
            }

            // Upcoming (Botão + funcional)
            item {
                SectionHeaderWithAction("Upcoming") {
                    viewModel.showAddDialog = true // Abre o popup
                }
            }

            if (upcomingTasks.isEmpty()) {
                item { Text("No upcoming tasks!", color = Color.Gray, modifier = Modifier.padding(8.dp)) }
            } else {
                items(upcomingTasks) { task ->
                    ScheduleTaskRow(
                        item = task,
                        onCheckClick = { viewModel.toggleTask(task.id) }
                    )
                }
            }

            // Completed Today
            if (completedTasks.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4CAF50))
                        Text(
                            text = "Completed Today",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                            color = Color(0xFF4CAF50),
                            modifier = Modifier.padding(start = 8.dp)
                        )                    }
                }
                items(completedTasks) { task ->
                    ScheduleTaskRow(
                        item = task,
                        onCheckClick = { viewModel.toggleTask(task.id) } // Permite "desfazer" a conclusão
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}