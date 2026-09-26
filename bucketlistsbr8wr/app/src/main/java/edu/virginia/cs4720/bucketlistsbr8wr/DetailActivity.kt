package edu.virginia.cs4720.bucketlistsbr8wr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import edu.virginia.cs4720.bucketlistsbr8wr.ui.theme.Bucketlistsbr8wrTheme

class DetailActivity : ComponentActivity() {
    private val vm: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "DetailActivity onCreate")
        val id = intent.getStringExtra("ITEM_ID") ?: ""

        enableEdgeToEdge()
        setContent {
            Bucketlistsbr8wrTheme {
                DetailScreen(
                    id = id,
                    vm = vm,
                    onDone = { finish() }
                )
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d("LIFECYCLE", "DetailActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("LIFECYCLE", "DetailActivity onResume") }
    override fun onPause() { super.onPause(); Log.d("LIFECYCLE", "DetailActivity onPause") }
    override fun onStop() { super.onStop(); Log.d("LIFECYCLE", "DetailActivity onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("LIFECYCLE", "DetailActivity onDestroy") }
}