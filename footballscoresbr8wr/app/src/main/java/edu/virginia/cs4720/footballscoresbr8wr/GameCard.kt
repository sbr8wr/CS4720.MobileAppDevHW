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
            .padding(vertical = 6.dp),
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
                Text(
                    text = game.gameState.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                val statusText = when (game.gameState.lowercase()) {
                    "live", "in_progress" -> {
                        val clock = if (game.contestClock.isNotBlank() && game.contestClock != "-0:00") game.contestClock else ""
                        val period = game.currentPeriod.ifBlank { "LIVE" }
                        "$period $clock".trim()
                    }
                    "final", "complete" -> {
                        val periodUpper = game.currentPeriod.uppercase()
                        if (periodUpper.contains("OT") || (periodUpper.isNotBlank() && periodUpper != "4TH" && periodUpper != "FINAL")) {
                            "FINAL / $periodUpper"
                        } else {
                            "FINAL"
                        }
                    }
                    else -> {
                        val timeStr = if (game.startTime.isNotBlank()) game.startTime else ""
                        "${game.startDate} $timeStr".trim()
                    }
                }

                Text(
                    text = statusText,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Away Team (Away vs Home identification)
            TeamScoreRow(
                teamName = game.awayTeamName,
                score = game.awayTeamScore,
                isWinner = game.awayTeamWinner,
                isHome = false
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Home Team
            TeamScoreRow(
                teamName = game.homeTeamName,
                score = game.homeTeamScore,
                isWinner = game.homeTeamWinner,
                isHome = true
            )
        }
    }
}

@Composable
fun TeamScoreRow(teamName: String, score: String, isWinner: Boolean, isHome: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = teamName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(modifier = Modifier.width(8.dp))
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
        }
        Text(
            text = score.ifBlank { "0" },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
        )
    }
}