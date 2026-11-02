package edu.virginia.cs4720.footballscoresbr8wr

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE division = :division AND year = :year AND week = :week")
    fun observeGames(division: String, year: Int, week: Int): Flow<List<GameEntity>>

    @Upsert
    suspend fun upsertGames(games: List<GameEntity>)
}