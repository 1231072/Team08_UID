package com.example.uid_finalproject.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.uid_finalproject.model.FamilyMember
import com.example.uid_finalproject.model.SettingLinkItem
import com.example.uid_finalproject.model.SettingToggleItem
import androidx.compose.material.icons.automirrored.filled.*

@Composable
fun FamilyProfileCard(name: String, email: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE3F2FD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF1976D2))
            }

            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
        }
    }
}

@Composable
fun FamilyMemberCard(member: FamilyMember, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = member.emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = member.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SettingSwitchRow(item: SettingToggleItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Medium)
            if (item.subtitle != null) {
                Text(text = item.subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
        Switch(checked = item.isChecked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingSliderRow(title: String, value: Float, valueText: String, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(text = valueText, color = Color.Gray)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black,
                inactiveTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun AppearanceSelector(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
       //light mode
        Card(
            modifier = Modifier.weight(1f).height(80.dp).clickable { onThemeChange(false) },
            border = if (!isDarkTheme) BorderStroke(2.dp, Color(0xFF2962FF)) else null,
            colors = CardDefaults.cardColors(containerColor = if (!isDarkTheme) Color.White else Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val color = if (!isDarkTheme) Color(0xFF2962FF) else Color.Gray
                Icon(Icons.Outlined.WbSunny, contentDescription = null, tint = color)
                Text("Light Mode", fontWeight = if (!isDarkTheme) FontWeight.Bold else FontWeight.Medium, color = color)
            }
        }

        // Dark Mode
        Card(
            modifier = Modifier.weight(1f).height(80.dp).clickable { onThemeChange(true) },
            border = if (isDarkTheme) BorderStroke(2.dp, Color(0xFF2962FF)) else null,
            colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) Color.White else Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val color = if (isDarkTheme) Color(0xFF2962FF) else Color.Gray
                Icon(Icons.Outlined.DarkMode, contentDescription = null, tint = color)
                Text("Dark Mode", fontWeight = if (isDarkTheme) FontWeight.Bold else FontWeight.Medium, color = color)
            }
        }
    }
}

@Composable
fun SettingLinkRow(text: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
        }
    }
}

@Composable
fun SignOutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = Color(0xFFD32F2F))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Sign Out", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
    }
}