package edu.virginia.cs4720.footballscoresbr8wr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.virginia.cs4720.footballscoresbr8wr.ui.theme.Footballscoresbr8wrTheme
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val gameDao = database.gameDao()

        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ncaa-api.henrygd.me/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        val ncaaApi = retrofit.create(NcaaAPI::class.java)

        val repository = GameRepository(ncaaApi, gameDao)

        setContent {
            Footballscoresbr8wrTheme {
                val viewModel: GameViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return GameViewModel(repository) as T
                        }
                    }
                )

                GameScreen(vm = viewModel)
            }
        }
    }
}