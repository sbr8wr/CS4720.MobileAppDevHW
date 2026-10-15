package edu.virginia.cs4720.footballscoresbr8wr

import kotlinx.coroutines.flow.Flow

class GameRepository(
    private val api: NcaaAPI,
    private val dao: GameDao
) {
    fun observeGames(division: String, year: Int, week: Int): Flow<List<GameEntity>> {
        return dao.observeGames(division, year, week)
    }

    suspend fun refreshGames(division: String, year: Int, week: Int) {
        try {
            val response = api.getScoreboard(division, year, week)

            val entities = response.games.map { wrapper ->
                val game = wrapper.game
                GameEntity(
                    gameId = game.gameID,
                    division = division,
                    year = year,
                    week = week,
                    gameState = game.gameState,
                    startDate = game.startDate,
                    startTime = game.startTime,
                    currentPeriod = game.currentPeriod,
                    contestClock = game.contestClock,
                    homeTeamName = game.home.names.shortName,
                    homeTeamScore = game.home.score,
                    homeTeamWinner = game.home.winner,
                    awayTeamName = game.away.names.shortName,
                    awayTeamScore = game.away.score,
                    awayTeamWinner = game.away.winner
                )
            }

            dao.insertGames(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}