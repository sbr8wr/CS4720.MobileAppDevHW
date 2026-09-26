package edu.virginia.cs4720.bucketlistsbr8wr

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun ListScreen(vm: ListViewModel) {
    val context = LocalContext.current

    val sorted = vm.items().sortedWith(
        compareBy({ it.done }, { it.dueDate })
    )

    Column(Modifier.padding(24.dp)) {
        Text("UVA Bucket List")

        Button(onClick = {
            val intent = Intent(context, CreateActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("+ Add New Item")
        }

        LazyColumn {
            items(sorted) { item ->
                BucketRow(
                    name = item.name,
                    dueDate = item.dueDate,
                    done = item.done,
                    onToggle = { vm.toggle(item.id) },
                    onEdit = {
                        val i = Intent(context, DetailActivity::class.java)
                        i.putExtra("ITEM_ID", item.id)
                        context.startActivity(i)
                    }
                )
            }
        }
    }
}

@Composable
fun BucketRow(
    name: String,
    dueDate: LocalDate,
    done: Boolean,
    onToggle: () -> Unit,
    onEdit: () -> Unit
) {
    Row(Modifier.padding(8.dp)) {
        Checkbox(
            checked = done,
            onCheckedChange = { onToggle() }
        )
        Column(Modifier.weight(1f)) {
            Text(name)
            Text("Due: $dueDate")
        }
        Button(onClick = onEdit) {
            Text("Edit")
        }
    }
}