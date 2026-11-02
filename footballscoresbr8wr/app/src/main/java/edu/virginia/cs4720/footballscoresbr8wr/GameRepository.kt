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
                currentPeriod = game.currentPeriod.orEmpty(),
                contestClock = game.contestClock.orEmpty(),
                finalMessage = game.finalMessage.orEmpty(),
                homeTeamName = game.home.names.shortName.takeIf { !it.isNullOrBlank() }
                    ?: game.home.names.char6.orEmpty().ifBlank { "Home Team" },
                homeTeamScore = game.home.score.orEmpty(),
                homeTeamWinner = game.home.winner,
                awayTeamName = game.away.names.shortName.takeIf { !it.isNullOrBlank() }
                    ?: game.away.names.char6.orEmpty().ifBlank { "Away Team" },
                awayTeamScore = game.away.score.orEmpty(),
                awayTeamWinner = game.away.winner
            )
        }

        // Upsert ensures stale rows are updated, new ones inserted, without duplicates
        dao.upsertGames(entities)
    }
}