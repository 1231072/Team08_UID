package com.example.uid_finalproject.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uid_finalproject.model.*

// 1. Cartão de Resumo com Gráfico de Linha (Canvas desenhado à mão)
@Composable
fun EnergySummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Today's Consumption", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "12.4", fontSize = 48.sp, fontWeight = FontWeight.Bold)
                Text(text = " kWh", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp), color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "↘ 8% vs yesterday", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
            }
            Text(text = "Estimated cost: $3.72", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Linha Simulado
            Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // Pontos do gráfico (simulados)
                    val path = Path().apply {
                        moveTo(0f, height * 0.8f)
                        cubicTo(width * 0.2f, height * 0.9f, width * 0.3f, height * 0.5f, width * 0.4f, height * 0.4f)
                        cubicTo(width * 0.5f, height * 0.3f, width * 0.7f, height * 0.1f, width * 0.8f, height * 0.2f)
                        lineTo(width, height * 0.6f)
                    }

                    // Desenha a linha azul
                    drawPath(
                        path = path,
                        color = Color(0xFF448AFF),
                        style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Desenha os pontinhos (círculos)
                    drawCircle(Color(0xFF448AFF), radius = 10f, center = Offset(0f, height * 0.8f))
                    drawCircle(Color(0xFF448AFF), radius = 10f, center = Offset(width * 0.4f, height * 0.4f))
                    drawCircle(Color(0xFF448AFF), radius = 10f, center = Offset(width * 0.8f, height * 0.2f))
                    drawCircle(Color(0xFF448AFF), radius = 10f, center = Offset(width, height * 0.6f))
                }

                // Labels do eixo X
                Row(
                    modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("00:00", fontSize = 10.sp, color = Color.Gray)
                    Text("08:00", fontSize = 10.sp, color = Color.Gray)
                    Text("16:00", fontSize = 10.sp, color = Color.Gray)
                    Text("23:00", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
    }
}

// 2. Gráfico de Barras (Weekly)
@Composable
fun WeeklyBarChart(data: List<WeeklyBarData>) {
    Row(
        modifier = Modifier.fillMaxWidth().height(180.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // A barra em si
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .fillMaxHeight(item.value) // Altura baseada no valor (0.0 a 1.0)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(if(item.isSelected) Color(0xFF6200EA) else Color(0xFF9575CD))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.day, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

// 3. Linha de Consumo por Quarto
@Composable
fun RoomConsumptionRow(item: RoomEnergyItem) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.name, fontWeight = FontWeight.Medium)
            Text(text = "${item.kwh} (${(item.percent * 100).toInt()}%)", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { item.percent },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            color = item.color,
            trackColor = Color(0xFFEEEEEE),
        )
    }
}

// 4. Item Top Consumidor (Ícone + Texto)
@Composable
fun ConsumerRow(item: EnergyConsumerItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFFAFAFA), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone quadrado
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(item.color, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = item.icon, contentDescription = null, tint = item.iconColor)
        }

        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = item.kwh, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Text(text = item.percent, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}

// 5. Item de Automação (Switch)
@Composable
fun AutomationRow(item: EnergyAutomationItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(text = item.subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Switch(
                checked = item.isEnabled,
                onCheckedChange = {}
            )
        }
    }
}