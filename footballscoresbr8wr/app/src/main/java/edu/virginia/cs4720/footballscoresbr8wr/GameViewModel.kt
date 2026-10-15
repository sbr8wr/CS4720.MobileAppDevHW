package edu.virginia.cs4720.footballscoresbr8wr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    var uiState by mutableStateOf(GameUIState())
        private set

    private var observationJob: Job? = null

    init {
        observeCurrentSelection()
        loadScores()
    }

    private fun observeCurrentSelection() {
        observationJob?.cancel()
        observationJob = viewModelScope.launch {
            repository.observeGames(uiState.division, uiState.year, uiState.week)
                .collectLatest { gamesFromDb ->
                    uiState = uiState.copy(games = gamesFromDb)
                }
        }
    }

    fun loadScores() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                repository.refreshGames(uiState.division, uiState.year, uiState.week)
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Failed to load scores"
                )
            }
        }
    }

    fun setDivision(newDivision: String) {
        if (uiState.division != newDivision) {
            uiState = uiState.copy(division = newDivision)
            observeCurrentSelection()
            loadScores()
        }
    }

    fun setWeek(newWeek: Int) {
        if (uiState.week != newWeek) {
            uiState = uiState.copy(week = newWeek)
            observeCurrentSelection()
            loadScores()
        }
    }
}