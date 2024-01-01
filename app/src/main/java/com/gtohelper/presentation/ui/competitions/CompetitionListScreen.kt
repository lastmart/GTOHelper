package com.gtohelper.presentation.ui.competitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.components.composables.AppSearchField
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.ui.competitions.components.CompetitionItem

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
        onAddButtonClicked = { navController.navigate("add_competition") },
        onItemClicked = { navController.navigate("detail/${it.id}") }
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
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.competitions))
                },
            )
        },
        floatingActionButton = {
            TransparentAddFab(
                onClick = onAddButtonClicked, contentDescription = null
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            AppSearchField(
                value = searchQuery,
                hint = "Поиск соревнований...",
                onValueChange = onSearchQueryChanged,
            )

            Spacer(Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(uiState.competitions) {
                    CompetitionItem(it, onClick = onItemClicked)
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    CompetitionListScreen(
        searchQuery = "",
        uiState = CompetitionListUiState(
            competitions = (0..5).map {
                Competition(1, "11 'Ы'", "Сдача нормативов")
            }.toList()
        )
    )
}