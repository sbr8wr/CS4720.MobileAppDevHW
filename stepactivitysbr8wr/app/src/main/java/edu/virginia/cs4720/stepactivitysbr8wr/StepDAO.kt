package edu.virginia.cs4720.stepactivitysbr8wr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    // Fetches all past records, newest first, for history screen & bar chart
    @Query("SELECT * FROM daily_steps ORDER BY date DESC")
    fun getAllSteps(): Flow<List<DailyStepRecord>>

    // Fetches a specific day's record
    @Query("SELECT * FROM daily_steps WHERE date = :date")
    suspend fun getStepRecordForDate(date: String): DailyStepRecord?

    // Inserts a record, or replaces it if the date already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: DailyStepRecord)
}