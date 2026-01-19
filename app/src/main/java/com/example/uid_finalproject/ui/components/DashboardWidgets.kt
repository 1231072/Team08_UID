package com.example.uid_finalproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.uid_finalproject.model.HomeStatusItem
import com.example.uid_finalproject.model.RoomItem

// red altert
@Composable
fun AlertBanner(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD32F2F)) // Vermelho
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

// status cards
@Composable
fun StatusCard(item: HomeStatusItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(item.color)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = item.icon, contentDescription = null, tint = item.textColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.title, fontSize = 12.sp, color = Color.Gray)
        Text(text = item.value, fontWeight = FontWeight.Bold, color = item.textColor)
    }
}

// bedroom list item
@Composable
fun RoomListItem(room: RoomItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE0E0E0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Aqui entraria o ícone real
            }

            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(text = room.name, fontWeight = FontWeight.Bold)
                Text(text = room.info, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(if (room.isOnline) Color.Green else Color.Red, CircleShape)
            )
        }
    }
}

// header
@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}