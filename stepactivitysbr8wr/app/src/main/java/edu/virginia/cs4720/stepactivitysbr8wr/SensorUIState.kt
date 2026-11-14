package edu.virginia.cs4720.stepactivitysbr8wr

// Holds values that Sensor View Model will need. Separating UI and Logic.
data class SensorUIState(
    // Values are floats as sensors return precise decimals.
    val xVal: Float = 0f,
    val yVal: Float = 0f,
    val zVal: Float = 0f,
    val liveSteps: Int = 0,
    // Permission granted to StepCounter or not.
    val permissionDenied: Boolean = false
)