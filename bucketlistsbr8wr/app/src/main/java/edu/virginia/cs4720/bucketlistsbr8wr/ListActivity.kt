package edu.virginia.cs4720.bucketlistsbr8wr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import edu.virginia.cs4720.bucketlistsbr8wr.ui.theme.Bucketlistsbr8wrTheme

class ListActivity : ComponentActivity() {
    private val vm: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "ListActivity onCreate")
        enableEdgeToEdge()
        setContent {
            Bucketlistsbr8wrTheme {
                ListScreen(vm = vm)
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d("LIFECYCLE", "ListActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("LIFECYCLE", "ListActivity onResume") }
    override fun onPause() { super.onPause(); Log.d("LIFECYCLE", "ListActivity onPause") }
    override fun onStop() { super.onStop(); Log.d("LIFECYCLE", "ListActivity onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("LIFECYCLE", "ListActivity onDestroy") }
}