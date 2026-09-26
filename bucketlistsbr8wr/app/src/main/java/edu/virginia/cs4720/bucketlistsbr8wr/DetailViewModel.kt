package edu.virginia.cs4720.bucketlistsbr8wr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class DetailViewModel : ViewModel() {
    private var loadedId: String? = null

    var name by mutableStateOf("")
    var dueDate by mutableStateOf<LocalDate?>(null)
    var done by mutableStateOf(false)
    var completedDate by mutableStateOf<LocalDate?>(null)
    var showDatePicker by mutableStateOf(false)

    fun start(id: String) {
        if (loadedId != null) return
        val item = BucketRepository.items.first { it.id == id }
        loadedId = id
        name = item.name
        dueDate = item.dueDate
        done = item.done
        completedDate = item.completedDate
    }

    fun canSave(): Boolean {
        return name.isNotBlank() && dueDate != null
    }

    fun toggleCompletion(checked: Boolean) {
        done = checked
        completedDate = if (checked) LocalDate.now() else null
    }

    fun save() {
        val id = loadedId ?: return
        val date = dueDate ?: return
        val original = BucketRepository.items.first { it.id == id }

        BucketRepository.update(
            original.copy(
                name = name,
                dueDate = date,
                done = done,
                completedDate = completedDate
            )
        )
    }
}