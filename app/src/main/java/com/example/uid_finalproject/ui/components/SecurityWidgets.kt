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
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.ui.window.Dialog
import com.example.uid_finalproject.model.EntryState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle

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


@Composable
fun EntryPointRow(item: EntryPointItem, onStatusClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val iconColor = if(item.state == EntryState.OPEN || item.state == EntryState.CURRENTLY_OPEN) Color.Red else Color(0xFF4CAF50)

        Icon(imageVector = item.icon, contentDescription = null, tint = iconColor)
        Text(
            text = item.name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f).padding(start = 16.dp)
        )


        Surface(
            color = item.state.color,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.clickable { onStatusClick() }
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

@Composable
fun LargeActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(56.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SecuritySafeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(text = "System Safe", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Text(text = "No alerts detected in Kids Room", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
            }
        }
    }
}

@Composable
fun CameraDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                    Text("Live Feed: Kids Room", color = Color.White, modifier = Modifier.padding(top = 8.dp))
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Text("X", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}