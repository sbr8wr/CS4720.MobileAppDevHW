package edu.virginia.cs4720.footballscoresbr8wr

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(vm: GameViewModel) {
    val state = vm.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("College Football Scores") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Segmented Division Toggle & Dropdown Week Selector Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.weight(1f)) {
                    SegmentedButton(
                        selected = state.division == "fbs",
                        onClick = { vm.setDivision("fbs") },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                    ) { Text("FBS") }
                    SegmentedButton(
                        selected = state.division == "fcs",
                        onClick = { vm.setDivision("fcs") },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                    ) { Text("FCS") }
                }

                WeekDropdownSelector(
                    selectedWeek = state.week,
                    onWeekSelected = { vm.setWeek(it) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Always visible linear indicator when fetching updates
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Screen Content State handling
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading && state.games.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    // Error state (No Internet & No local Room DB cached games)
                    state.errorMessage != null && state.games.isEmpty() -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Couldn't load scores. Check your network connection.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = { vm.loadScores() }) {
                                Text("Retry")
                            }
                        }
                    }

                    // Empty state (Valid request returning zero games)
                    !state.isLoading && state.games.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No games scheduled for Week ${state.week}.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Data State: Pull-To-Refresh around LazyColumn
                    else -> {
                        PullToRefreshBox(
                            isRefreshing = state.isLoading,
                            onRefresh = { vm.loadScores() },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(state.games, key = { it.gameId }) { game ->
                                    GameCard(game = game)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDropdownSelector(
    selectedWeek: Int,
    onWeekSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = "Week $selectedWeek",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (1..14).forEach { w ->
                DropdownMenuItem(
                    text = { Text("Week $w") },
                    onClick = {
                        onWeekSelected(w)
                        expanded = false
                    }
                )
            }
        }
    }
}