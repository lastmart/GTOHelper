package com.gtohelper.presentation.ui.competition_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.components.composables.LoadFailedScreen
import com.gtohelper.presentation.components.composables.LoadingScreen
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun CompetitionDetailRoute(
    navController: NavController,
    viewModel: CompetitionDetailViewModel,
    competitionId: Int,
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(context) {
        viewModel.isDeleted.receiveAsFlow().collect {
            if (it) {
                navController.navigateUp()
            }
        }
    }

    CompetitionDetailScreen(
        uiState = uiState,
        onCompetitorsClicked = { navController.navigate("competitors/$competitionId") },
        onDisciplinesClicked = { navController.navigate("disciplines/$competitionId") },
        onResultsClicked = { navController.navigate("results/$competitionId") },
        onBackClicked = navController::navigateUp,
        onDeleteClicked = viewModel::delete,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionDetailScreen(
    uiState: CompetitionDetailUiState = CompetitionDetailUiState.Loading,
    onCompetitorsClicked: () -> Unit = {},
    onDisciplinesClicked: () -> Unit = {},
    onResultsClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
) {
    when (uiState) {
        CompetitionDetailUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())

        is CompetitionDetailUiState.Loaded -> {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(uiState.competition.name)
                    }, navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }, actions = {
                        IconButton(onClick = onDeleteClicked) {
                            Icon(
                                imageVector = Icons.Default.Delete, contentDescription = null
                            )
                        }
                    })
                },

                ) { padding ->
                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    Text(uiState.competition.name)

                    Text(uiState.competition.description)

                    Button(onClick = onCompetitorsClicked) {
                        Text(stringResource(R.string.competitors, uiState.competitorsCount))
                    }

                    Button(onClick = onDisciplinesClicked) {
                        Text(stringResource(R.string.disciplines))
                    }

                    Button(onClick = onResultsClicked) {
                        Text(stringResource(R.string.results))
                    }
                }
            }
        }
        else -> Unit
    }
}

@Preview
@Composable
fun PreviewLoading() {
    CompetitionDetailScreen(
        uiState = CompetitionDetailUiState.Loading
    )
}


@Preview
@Composable
fun PreviewLoadFailed() {
    CompetitionDetailScreen(
        uiState = CompetitionDetailUiState.LoadFailed
    )
}


@Preview
@Composable
fun PreviewLoaded() {
    CompetitionDetailScreen(
        uiState = CompetitionDetailUiState.Loaded(
            Competition(0, "Name", "Description"),
            10
        )
    )
}