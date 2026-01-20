package com.example.uid_finalproject.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uid_finalproject.MainViewModel
import com.example.uid_finalproject.model.*
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    val weeklyData = listOf(
        WeeklyBarData("Mon", 0.6f),
        WeeklyBarData("Tue", 0.5f),
        WeeklyBarData("Wed", 0.8f, true),
        WeeklyBarData("Thu", 0.55f),
        WeeklyBarData("Fri", 0.7f),
        WeeklyBarData("Sat", 0.75f),
        WeeklyBarData("Sun", 0.65f)
    )

    val roomConsumption = listOf(
        RoomEnergyItem("Living Room", "4.2 kWh", 0.28f, Color(0xFF2962FF)),
        RoomEnergyItem("Kids Room", "2.8 kWh", 0.19f, Color(0xFF2962FF)),
        RoomEnergyItem("Master Bedroom", "2.1 kWh", 0.14f, Color(0xFF2962FF)),
        RoomEnergyItem("Kitchen", "3.5 kWh", 0.23f, Color(0xFF2962FF)),
        RoomEnergyItem("Others", "2.4 kWh", 0.16f, Color(0xFF2962FF))
    )

    val topConsumers = listOf(
        EnergyConsumerItem("Lighting", "3.2 kWh", "26%", Icons.Default.Lightbulb, Color(0xFFFFF9C4), Color(0xFFFBC02D)),
        EnergyConsumerItem("Heating/Cooling", "4.8 kWh", "39%", Icons.Default.AcUnit, Color(0xFFE3F2FD), Color(0xFF1976D2)),
        EnergyConsumerItem("Entertainment", "2.1 kWh", "17%", Icons.Default.Tv, Color(0xFFF3E5F5), Color(0xFF7B1FA2)),
        EnergyConsumerItem("Appliances", "2.3 kWh", "18%", Icons.Default.Kitchen, Color(0xFFE8F5E9), Color(0xFF388E3C))
    )

    val automations = listOf(
        EnergyAutomationItem("Turn off heating when away", "When: No one home\nThen: Set thermostat to 18°C", true),
        EnergyAutomationItem("Night mode lighting", "When: After 10 PM\nThen: Dim all lights to 30%", true),
        EnergyAutomationItem("Morning energy save", "When: Before 6 AM\nThen: Turn off non-essential devices", false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Energy", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") } }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(navController = navController, currentRoute = Routes.ENERGY)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }

            item { EnergySummaryCard() }

            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Weekly Overview", style = MaterialTheme.typography.titleMedium)
                            FilterButtons(selectedFilter = viewModel.energyFilter, onFilterSelected = { viewModel.energyFilter = it })
                        }
                        WeeklyBarChart(weeklyData)

                        Divider(modifier = Modifier.padding(vertical = 16.dp))

                        Text("Consumption by Room", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
                        roomConsumption.forEach { room ->
                            RoomConsumptionRow(room)
                        }
                    }
                }
            }

            item { SectionHeader("Top Consumers Today") }
            items(topConsumers) { item ->
                ConsumerRow(item)
            }

            item { SectionHeader("Energy Automation") }
            items(automations) { item ->
                AutomationRow(item)
            }

            item {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LargeActionButton(
                        text = "Eco Mode",
                        icon = Icons.Default.Eco,
                        backgroundColor = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                    LargeActionButton(
                        text = "View Report",
                        icon = Icons.Default.Bolt,
                        backgroundColor = Color(0xFF2962FF),
                        modifier = Modifier.weight(1f),
                        onClick = { 
                            val report = generateEnergyReport(roomConsumption, topConsumers)
                            saveReportToFile(context, "energy_report.txt", report)
                            Toast.makeText(context, "Report saved to energy_report.txt", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun FilterButtons(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    val filters = listOf("Real-Time", "Day", "Week", "Month")
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        filters.forEach { filter ->
            val isSelected = selectedFilter == filter
            Button(
                onClick = { onFilterSelected(filter) },
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(filter, fontSize = 12.sp)
            }
        }
    }
}

fun generateEnergyReport(roomConsumption: List<RoomEnergyItem>, topConsumers: List<EnergyConsumerItem>): String {
    val builder = StringBuilder()
    builder.append("Energy Report\n")
    builder.append("===========\n\n")
    builder.append("Consumption by Room:\n")
    for (item in roomConsumption) {
        builder.append("- ${item.name}: ${item.kwh}\n")
    }
    builder.append("\nTop Consumers Today:\n")
    for (item in topConsumers) {
        builder.append("- ${item.title}: ${item.kwh} (${item.percent})\n")
    }
    return builder.toString()
}

fun saveReportToFile(context: Context, fileName: String, content: String) {
    val file = File(context.filesDir, fileName)
    FileOutputStream(file).use {
        it.write(content.toByteArray())
    }
}
