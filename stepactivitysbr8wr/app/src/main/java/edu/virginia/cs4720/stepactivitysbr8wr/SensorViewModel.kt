package edu.virginia.cs4720.stepactivitysbr8wr

// AI Assisted
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class SensorViewModel(application: Application) : AndroidViewModel(application) {

    private val stepDao = AppDatabase.getDatabase(application).stepDao()

    // Expose history records reactively from Room
    val historyRecords: StateFlow<List<DailyStepRecord>> = stepDao.getAllSteps()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UI State
    var uiState by mutableStateOf(SensorUIState())
        private set

    // Hardware Step Counter Baseline Tracking
    private var initialHardwareSteps = -1f

    // AI Assisted
    // Logic for Counting Steps using Accelerometer
    // Fallback Peak Detection Variables
    // Longs for time
    private var lastPeakTime = 0L
    // 0 until first 'step' is taken
    private var peakThreshold = 11.5f
    // Since gravity is 9.81, using 10 is too close, and want to make sure steps are different from other actions, so initially set to 11.5f
    private var minStepIntervalMs = 250L
    // 0.25 seconds delay to prevent double counting, and other actions from counting as 'steps'

    // Function: Update steps using Accelerometer
    fun updateAccelerometer(x: Float, y: Float, z: Float) {
        // Calculate the scalar magnitude based on the x,y,z vectors that the accelerometer gives,
        val magnitude = sqrt(x * x + y * y + z * z).toDouble().toFloat()
        val currentTime = System.currentTimeMillis()

        // If the accelerometer measures that movement is higher than the threshold and the time fits the interval
        // Then it can be assumed that a step was taken.
        var newSteps = uiState.liveSteps
        if (magnitude > peakThreshold && (currentTime - lastPeakTime) > minStepIntervalMs) {
            lastPeakTime = currentTime
            newSteps++
        }

        // Copy new values into state
        uiState = uiState.copy(
            xVal = x,
            yVal = y,
            zVal = z,
            liveSteps = newSteps
        )
        saveTodaySteps(newSteps)

    }

    // Function: Update steps using StepCounter
    fun updateHardwareStepCount(rawSensorValue: Float) {
        // Initialization
        if (initialHardwareSteps < 0f) {
            initialHardwareSteps = rawSensorValue
        }
        // Makes sure starts at 0 every day
        val calculatedSteps = (rawSensorValue - initialHardwareSteps).toInt()
        uiState = uiState.copy(liveSteps = calculatedSteps)
        saveTodaySteps(calculatedSteps)
    }

    // Stores whether StepCounter is allowed or not.
    fun setPermissionDenied(denied: Boolean) {
        uiState = uiState.copy(permissionDenied = denied)
    }

    // Saves total steps taken today
    private fun saveTodaySteps(steps: Int) {
        // "YYYY-MM-DD"
        val today = LocalDate.now().toString()
        viewModelScope.launch {
            stepDao.insertOrUpdate(DailyStepRecord(date = today, stepCount = steps))
        }
    }
}