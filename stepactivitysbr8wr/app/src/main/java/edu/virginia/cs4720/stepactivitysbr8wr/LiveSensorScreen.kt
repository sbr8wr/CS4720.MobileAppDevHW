package edu.virginia.cs4720.stepactivitysbr8wr

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LiveSensorScreen(
    // Get UI State and Lambda Function that returns nothing
    // Lambda function used to request permission for StepCounter
    uiState: SensorUIState,
    onRequestPermission: () -> Unit
) {
    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Live Sensor Feed", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Accelerometer Readings Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Accelerometer (m/s²)", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("X: ${String.format("%.2f", uiState.xVal)}")
                Text("Y: ${String.format("%.2f", uiState.yVal)}")
                Text("Z: ${String.format("%.2f", uiState.zVal)}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Step Count Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Step Count (Hybrid)", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("${uiState.liveSteps}", style = MaterialTheme.typography.headlineLarge)
            }
        }

        // Permission Request UI (Respectful runtime handling)
        if (uiState.permissionDenied) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text("Grant Activity Recognition Permission")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Needed for hardware step counting. Live accelerometer fallback is active.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}