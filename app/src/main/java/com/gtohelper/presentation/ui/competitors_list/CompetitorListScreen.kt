package com.gtohelper.presentation.ui.competitors_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.gtohelper.presentation.navigation.Screen
import com.gtohelper.presentation.ui.competitors_list.components.composables.CompetitorItem
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun CompetitorListRoute(
    navController: NavController,
    viewModel: CompetitorsListViewModel,
    competitionId: Int,
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    CompetitorListScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::updateSearch,
        onBackClicked = navController::navigateUp,
        onAddCompetitorClicked = {
            navController.navigate("add_competitor/$competitionId")
        },
        onAddCompetitorFromTableClicked = {
            navController.navigate("add_competitor_from_table/$competitionId")
        },
        onItemClicked = {
            navController.navigate("edit_competitor/${it.id}")
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorListScreen(
    uiState: CompetitorListUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onItemClicked: (Competitor) -> Unit = {},
    onAddCompetitorClicked: () -> Unit = {},
    onAddCompetitorFromTableClicked: () -> Unit = {},
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column {
                Button(onClick = {
                    showBottomSheet = false
                    onAddCompetitorClicked()
                }) {
                    Text("Добавить вручную")
                }
                Button(onClick = {
                    showBottomSheet = false
                    onAddCompetitorFromTableClicked()
                }) {
                    Text("Добавить с помощью таблицы")
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.competitors, uiState.competitors.size))
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
                onClick = { showBottomSheet = true },
                contentDescription = null
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
                items(uiState.competitors) { item ->
                    CompetitorItem(
                        modifier = Modifier.fillMaxWidth(),
                        competitor = item,
                        onClick = onItemClicked
                    )
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