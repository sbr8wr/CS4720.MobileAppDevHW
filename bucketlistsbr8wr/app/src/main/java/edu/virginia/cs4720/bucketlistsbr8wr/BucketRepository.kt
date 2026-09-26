package edu.virginia.cs4720.bucketlistsbr8wr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

object BucketRepository {
    var items by mutableStateOf(
        listOf(
            BucketItem(
                name = "Eat at Bodo's at 6 AM",
                dueDate = LocalDate.now().plusDays(2)
            ),
            BucketItem(
                name = "Finish HW 1",
                dueDate = LocalDate.now().plusDays(5)
            ),
            BucketItem(
                name = "Go To CS4720 Exam",
                dueDate = LocalDate.now().minusDays(1),
                done = true,
                completedDate = LocalDate.now().minusDays(1)
            )
        )
    )
        private set

    fun add(item: BucketItem) {
        items = items + item
    }

    fun update(updated: BucketItem) {
        items = items.map { if (it.id == updated.id) updated else it }
    }
}