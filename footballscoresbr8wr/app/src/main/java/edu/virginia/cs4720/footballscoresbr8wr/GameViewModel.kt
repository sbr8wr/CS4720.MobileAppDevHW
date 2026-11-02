package edu.virginia.cs4720.footballscoresbr8wr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    var uiState by mutableStateOf(GameUIState(week = calculateDefaultWeek()))
        private set

    private var observationJob: Job? = null

    init {
        observeCurrentSelection()
        loadScores()
    }

    /**
     * Maps today's date to a week number (1..14).
     * Season starting late August / early September.
     */
    private fun calculateDefaultWeek(): Int {
        val today = LocalDate.now()
        val seasonStartDate = LocalDate.of(today.year, 8, 27) // Approx Week 1 start
        if (today.isBefore(seasonStartDate)) return 1

        val weeksBetween = ChronoUnit.WEEKS.between(seasonStartDate, today).toInt() + 1
        return weeksBetween.coerceIn(1, 14)
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
                    errorMessage = e.localizedMessage ?: "Couldn't load scores, check your connection"
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