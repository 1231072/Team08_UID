package com.example.uid_finalproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uid_finalproject.model.EntryPointItem
import com.example.uid_finalproject.model.MotionSensorItem
import com.example.uid_finalproject.model.SecurityStatusCount

// alert card
@Composable
fun SecurityAlertCard(title: String, message: String, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFF57F17))
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold, color = Color(0xFFF57F17))
                Text(text = message, style = MaterialTheme.typography.bodySmall, color = Color(0xFFF57F17))
            }
            Surface(
                color = Color(0xFFF57F17),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Alert",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// status card
@Composable
fun SecurityStatusSquare(item: SecurityStatusCount, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(item.color)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = item.icon, contentDescription = null, tint = item.contentColor, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.title, fontSize = 12.sp, color = Color.Gray)
        Text(text = item.count, fontWeight = FontWeight.Bold, color = item.contentColor, fontSize = 18.sp)
    }
}

// entry points
@Composable
fun EntryPointRow(item: EntryPointItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = null, tint = if(item.state.name.contains("OPEN")) Color.Red else Color(0xFF4CAF50))
        Text(
            text = item.name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f).padding(start = 16.dp)
        )

        // O Chip de estado (Open/Closed/Locked)
        Surface(
            color = item.state.color,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = item.state.label,
                color = item.state.textColor,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

// 4. Linha de Sensor de Movimento (Com Switch)
@Composable
fun MotionSensorRow(item: MotionSensorItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.location, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = item.statusText, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Switch(
            checked = item.isActive,
            onCheckedChange = {}
        )
    }
}

// 5. Botão Grande (Azul ou Vermelho)
@Composable
fun LargeActionButton(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(56.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}