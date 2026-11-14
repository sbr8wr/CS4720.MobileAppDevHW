package edu.virginia.cs4720.stepactivitysbr8wr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_steps")
data class DailyStepRecord(
    // Format: "YYYY-MM-DD"
    @PrimaryKey val date: String,
    val stepCount: Int
)