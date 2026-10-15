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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = game.gameState.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (game.contestClock.isNotBlank()) "${game.currentPeriod} - ${game.contestClock}" else game.startDate,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TeamScoreRow(
                teamName = game.awayTeamName,
                score = game.awayTeamScore,
                isWinner = game.awayTeamWinner
            )

            Spacer(modifier = Modifier.height(4.dp))

            TeamScoreRow(
                teamName = game.homeTeamName,
                score = game.homeTeamScore,
                isWinner = game.homeTeamWinner
            )
        }
    }
}

@Composable
fun TeamScoreRow(teamName: String, score: String, isWinner: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = score,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
        )
    }
}