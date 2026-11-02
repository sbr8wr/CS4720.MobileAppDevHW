package edu.virginia.cs4720.footballscoresbr8wr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GameCard(game: GameEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            // Header: Game State & Clock/Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val stateText = when (game.gameState.lowercase()) {
                    "live", "in_progress" -> "LIVE"
                    "final", "post" -> "FINAL"
                    else -> "UPCOMING"
                }

                Text(
                    text = stateText,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (stateText == "LIVE") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                val statusText = when (game.gameState.lowercase()) {
                    "live", "in_progress" -> {
                        val clock = if (game.contestClock.isNotBlank() && game.contestClock != "0:00" && game.contestClock != "-0:00") {
                            game.contestClock
                        } else ""
                        val period = if (game.currentPeriod.isNotBlank()) "Q${game.currentPeriod}" else "LIVE"
                        "$period $clock".trim()
                    }
                    "final", "post" -> {
                        when {
                            game.finalMessage.isNotBlank() -> game.finalMessage
                            game.currentPeriod.contains("OT", ignoreCase = true) -> "FINAL / ${game.currentPeriod.uppercase()}"
                            else -> "FINAL"
                        }
                    }
                    else -> {
                        val timeStr = if (game.startTime.isNotBlank()) game.startTime else ""
                        "${game.startDate} $timeStr".trim()
                    }
                }

                Text(
                    text = statusText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Away Team
            TeamScoreRow(
                teamName = game.awayTeamName,
                score = game.awayTeamScore,
                isWinner = game.awayTeamWinner,
                isHome = false,
                isFinal = game.gameState.lowercase() in listOf("final", "post")
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Home Team
            TeamScoreRow(
                teamName = game.homeTeamName,
                score = game.homeTeamScore,
                isWinner = game.homeTeamWinner,
                isHome = true,
                isFinal = game.gameState.lowercase() in listOf("final", "post")
            )
        }
    }
}

@Composable
fun TeamScoreRow(
    teamName: String,
    score: String,
    isWinner: Boolean,
    isHome: Boolean,
    isFinal: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = if (isHome) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(
                    text = if (isHome) "HOME" else "AWAY",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    color = if (isHome) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = teamName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isWinner && isFinal) FontWeight.Bold else FontWeight.Normal
            )
        }
        Text(
            text = score.ifBlank { "-" },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isWinner && isFinal) FontWeight.Bold else FontWeight.Normal
        )
    }
}