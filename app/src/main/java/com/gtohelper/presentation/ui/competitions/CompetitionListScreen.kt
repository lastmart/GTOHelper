package com.gtohelper.presentation.ui.competitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.input_fields.AppSearchField
import com.gtohelper.presentation.components.composables.placeholder_screens.LoadingScreen
import com.gtohelper.presentation.navigation.Screen
import com.gtohelper.presentation.ui.competitions.components.CompetitionItem
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun CompetitionListRoute(
    navController: NavController,
    viewModel: CompetitionListViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    CompetitionListScreen(
        searchQuery = searchQuery,
        uiState = uiState,
        onSearchQueryChanged = viewModel::updateSearch,
        onAddButtonClicked = { navController.navigate(Screen.AddCompetitionScreen.route) },
        onItemClicked = {
            navController.navigate(
                Screen.DisciplinesListScreen.withArgs(
                    it.id.toString(),
                    it.name
                )
            )
        },
        onHelpClick = { navController.navigate(Screen.HelpScreen.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionListScreen(
    uiState: CompetitionListUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    onAddButtonClicked: () -> Unit = {},
    onItemClicked: (Competition) -> Unit = {},
    onHelpClick: () -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.competitions))
                },
                actions = {
                    IconButton(onClick = onHelpClick) {
                        Icon(
                            painter = painterResource(R.drawable.help_icon),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState is CompetitionListUiState.Loaded) {
                AddButton(
                    onClick = onAddButtonClicked, contentDescription = null
                )
            }
        },
    ) { padding->

        Column(modifier = Modifier.padding(padding)) {
            when (uiState) {
                CompetitionListUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                is CompetitionListUiState.Loaded -> {
                    AppSearchField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        hint = "Поиск соревнований...",
                        onValueChange = onSearchQueryChanged,
                    )
                    Spacer(Modifier.height(10.dp))
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 4.dp,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            items(uiState.competitions) {
                                CompetitionItem(it, onClick = onItemClicked)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoaded() {
    CompetitionListScreen(
        searchQuery = "",
        uiState = CompetitionListUiState.Loaded(
            competitions = (0..10).map {
                Competition(1,
                    name = (0..it).joinToString("") { "asd" },
                    description = (0..it).joinToString("") { "asd" },
                )
            }.toList()
        )
    )
}

@Preview
@Composable
fun PreviewLoading() {
    CompetitionListScreen(
        searchQuery = "",
        uiState = CompetitionListUiState.Loading
    )
}

