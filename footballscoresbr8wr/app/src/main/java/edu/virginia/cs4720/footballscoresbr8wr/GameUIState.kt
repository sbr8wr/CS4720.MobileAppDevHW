package edu.virginia.cs4720.footballscoresbr8wr

data class GameUIState(
    val games: List<GameEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val division: String = "fbs",
    val year: Int = 2026,
    val week: Int = 4
)