package edu.virginia.cs4720.bucketlistsbr8wr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import edu.virginia.cs4720.bucketlistsbr8wr.ui.theme.Bucketlistsbr8wrTheme

class CreateActivity : ComponentActivity() {
    private val vm: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "CreateActivity onCreate")
        enableEdgeToEdge()
        setContent {
            Bucketlistsbr8wrTheme {
                CreateScreen(
                    vm = vm,
                    onDone = { finish() }
                )
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d("LIFECYCLE", "CreateActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("LIFECYCLE", "CreateActivity onResume") }
    override fun onPause() { super.onPause(); Log.d("LIFECYCLE", "CreateActivity onPause") }
    override fun onStop() { super.onStop(); Log.d("LIFECYCLE", "CreateActivity onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("LIFECYCLE", "CreateActivity onDestroy") }
}