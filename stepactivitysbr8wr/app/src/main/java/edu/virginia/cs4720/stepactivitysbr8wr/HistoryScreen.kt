package edu.virginia.cs4720.stepactivitysbr8wr

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun HistoryScreen(records: List<DailyStepRecord>) {

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Daily Step History", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Bonus Bar Chart Card
        if (records.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Recent Activity Trend", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    // AI Assisted
                    // Simple Canvas Bar Chart
                    val maxSteps = records.maxOfOrNull { it.stepCount }?.coerceAtLeast(1000) ?: 1000

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val barWidth = size.width / (records.size * 2f).coerceAtLeast(2f)
                        val maxAllowedHeight = size.height - 20f

                        records.take(7).reversed().forEachIndexed { index, record ->
                            val barHeight = (record.stepCount.toFloat() / maxSteps) * maxAllowedHeight
                            val xPos = index * (barWidth * 2f) + barWidth / 2f
                            val yPos = size.height - barHeight

                            drawRect(
                                color = androidx.compose.ui.graphics.Color(0xFF6200EE),
                                topLeft = Offset(x = xPos, y = yPos),
                                size = Size(width = barWidth, height = barHeight)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // History List (Newest First)
        Text("Past Days", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(records) { record ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = record.date, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${record.stepCount} steps",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}