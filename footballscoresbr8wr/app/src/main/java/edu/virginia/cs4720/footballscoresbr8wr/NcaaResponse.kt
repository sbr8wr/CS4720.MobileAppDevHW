package edu.virginia.cs4720.footballscoresbr8wr

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NcaaResponse(
    val games: List<GameWrapper> = emptyList()
)

@Serializable
data class GameWrapper(
    @SerialName("game")
    val game: GameDetails
)

@Serializable
data class GameDetails(
    @SerialName("gameID")
    val gameID: String,

    @SerialName("gameState")
    val gameState: String,

    @SerialName("startDate")
    val startDate: String,

    @SerialName("startTime")
    val startTime: String,

    @SerialName("currentPeriod")
    val currentPeriod: String,

    @SerialName("contestClock")
    val contestClock: String,

    @SerialName("home")
    val home: TeamInfo,

    @SerialName("away")
    val away: TeamInfo
)

@Serializable
data class TeamInfo(
    @SerialName("score")
    val score: String,

    @SerialName("winner")
    val winner: Boolean,

    @SerialName("names")
    val names: TeamNames
)

@Serializable
data class TeamNames(
    @SerialName("short")
    val shortName: String
)