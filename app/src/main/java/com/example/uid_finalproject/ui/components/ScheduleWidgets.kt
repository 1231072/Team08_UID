package com.example.uid_finalproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uid_finalproject.model.ScheduleStatItem
import com.example.uid_finalproject.model.ScheduleTaskItem
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
fun ScheduleStatCard(item: ScheduleStatItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = item.color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.count,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2962FF)
            )
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}
@Composable
fun ScheduleTaskRow(item: ScheduleTaskItem, onCheckClick: () -> Unit) {
    val bgColor = if (item.isCompleted) Color(0xFFF5F5F5) else Color(0xFFFAFAFA)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(if (item.isCompleted) Color.LightGray else Color(0xFFE3F2FD), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (item.isCompleted) Color.Gray else Color(0xFF1976D2),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    style = if (item.isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(),
                    color = if (item.isCompleted) Color.Gray else Color.Black
                )
                Text(
                    text = item.timeAndPerson, // Usa a propriedade helper que criámos
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onCheckClick) {
                if (item.isCompleted) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.Transparent, RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(2.dp)
                            .background(Color(0xFFEEEEEE), RoundedCornerShape(6.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeaderWithAction(title: String, onAddClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        FilledIconButton(
            onClick = onAddClick,
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF2962FF))
        ) {
            Text("+", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, time: String, person: String, isMed: Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var person by remember { mutableStateOf("") }
    var isMedication by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Reminder", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title (e.g. Aspirin)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time (e.g. 14:00)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = person, onValueChange = { person = it }, label = { Text("Family Member") }, modifier = Modifier.fillMaxWidth())

                // Checkbox "Is Medication"
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isMedication, onCheckedChange = { isMedication = it })
                    Text("Is this a medication?")
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Button(
                        onClick = { onAdd(title, time, person, isMedication) },
                        enabled = title.isNotEmpty() && time.isNotEmpty()
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}