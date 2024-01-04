package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.components.composables.AppSearchField
import com.gtohelper.presentation.components.composables.LoadingScreen
import com.gtohelper.presentation.ui.disciplines_list.add_results.components.ResultItem
import com.gtohelper.presentation.ui.theme.spacing


@Composable
fun AddResultsRoute(
    navController: NavController,
    viewModel: AddResultsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    AddResultsScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultsScreen(
    uiState: AddResultsUiState,
    onEvent: (AddResultsEvent) -> Unit = {},
) {
    when (uiState) {
        AddResultsUiState.Loading -> LoadingScreen()
        is AddResultsUiState.Loaded -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(uiState.discipline.name) },
                        navigationIcon = {

                        }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    AppSearchField()
                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                    LazyColumn {
                        items(uiState.results) {
                            ResultItem(DisciplinePointType.TIME, result = it)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewAddResultsScreen() {
    AddResultsScreen(
        uiState = AddResultsUiState.Loaded(
            Discipline(
                0, "Прыжки с крыши",
            ),
            listOf(),
        )
    )
}




