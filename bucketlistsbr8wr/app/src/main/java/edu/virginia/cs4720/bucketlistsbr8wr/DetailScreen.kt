package edu.virginia.cs4720.bucketlistsbr8wr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String,
    vm: DetailViewModel,
    onDone: () -> Unit
) {
    vm.start(id)

    Column(Modifier.padding(24.dp)) {
        Text("Edit Bucket Item")

        TextField(
            value = vm.name,
            onValueChange = { vm.name = it },
            label = { Text("Task Description") }
        )

        Row {
            Checkbox(
                checked = vm.done,
                onCheckedChange = { vm.toggleCompletion(it) }
            )
            Text(
                text = "Completed" + if (vm.done && vm.completedDate != null) " (${vm.completedDate})" else ""
            )
        }

        Button(onClick = { vm.showDatePicker = true }) {
            Text(vm.dueDate?.let { "Due: $it" } ?: "Select Due Date")
        }

        Row {
            Button(
                onClick = {
                    vm.save()
                    onDone()
                },
                enabled = vm.canSave()
            ) {
                Text("Save")
            }

            Button(onClick = onDone) {
                Text("Cancel")
            }
        }
    }

    if (vm.showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { vm.showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        vm.dueDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                    }
                    vm.showDatePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}