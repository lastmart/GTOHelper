package com.gtohelper.presentation.ui.competitors_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.composables.AppSearchField
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.ui.competitors_list.components.composables.CompetitorItem
import com.gtohelper.theme.spacing

@Composable
fun CompetitorListRoute(
    navController: NavController,
    viewModel: CompetitorsListViewModel,
    competitorId: Int,
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    CompetitorListScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::updateSearch,
        onBackClicked = navController::navigateUp,
        onAddButtonClicked = { navController.navigate("add_competitor/$competitorId") },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorListScreen(
    uiState: CompetitorListUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onAddButtonClicked: () -> Unit = {},
    onItemClicked: (Competitor) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.competitors_list))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            TransparentAddFab(
                onClick = onAddButtonClicked, contentDescription = null
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {

            AppSearchField(
                value = searchQuery,
                hint = "Поиск участников...",
                onValueChange = onSearchQueryChanged,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                items(uiState.competitors) {
                    CompetitorItem(it, onEditClick = onItemClicked)
                }
            }
        }
    }
}

@Preview
@Composable
fun CompetitorListScreenPreview() {
    CompetitorListScreen(
        uiState = CompetitorListUiState(
            (0..11).map {
                Competitor(
                    1, 1, 1, "Name", Gender.MALE, "teamName", 2
                )
            }
        ),
        searchQuery = "",
    )
}