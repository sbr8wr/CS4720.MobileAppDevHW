package edu.virginia.cs4720.bucketlistsbr8wr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class CreateViewModel : ViewModel() {
    var name by mutableStateOf("")
    var dueDate by mutableStateOf<LocalDate?>(null)
    var showDatePicker by mutableStateOf(false)

    fun canSave(): Boolean {
        return name.isNotBlank() && dueDate != null
    }

    fun save() {
        val date = dueDate ?: return
        BucketRepository.add(
            BucketItem(
                name = name,
                dueDate = date
            )
        )
    }
}