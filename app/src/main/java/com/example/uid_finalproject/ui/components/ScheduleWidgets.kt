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
fun ScheduleTaskRow(item: ScheduleTaskItem) {
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
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    style = if (item.isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(),
                    color = if (item.isCompleted) Color.Gray else Color.Black
                )
                Text(
                    text = item.timeAndPerson,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            if (item.isCompleted) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color(0xFF4CAF50))
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .padding(2.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.LightGray)
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