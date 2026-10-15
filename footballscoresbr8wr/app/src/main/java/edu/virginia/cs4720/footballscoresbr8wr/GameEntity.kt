package edu.virginia.cs4720.footballscoresbr8wr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val gameId: String,
    val division: String,
    val year: Int,
    val week: Int,
    val gameState: String,
    val startDate: String,
    val startTime: String,
    val currentPeriod: String,
    val contestClock: String,
    val homeTeamName: String,
    val homeTeamScore: String,
    val homeTeamWinner: Boolean,
    val awayTeamName: String,
    val awayTeamScore: String,
    val awayTeamWinner: Boolean
)