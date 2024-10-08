package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.usecases.sport_result.SaveSportResultResult
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.placeholder_screens.LoadingScreen
import com.gtohelper.presentation.navigation.Screen
import com.gtohelper.presentation.ui.disciplines_list.add_results.components.ResultInputField
import com.gtohelper.presentation.ui.disciplines_list.add_results.components.ResultItem
import com.gtohelper.presentation.ui.theme.spacing


@Composable
fun AddResultsRoute(
    navController: NavController,
    viewModel: AddResultsViewModel,
    competitionId: Int,
    disciplineId: Int,
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val results by viewModel.results.collectAsState(initial = listOf())

    AddResultsScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        results = results,
        onEvent = viewModel::onEvent,
        onBackClicked = navController::navigateUp,
        onSearchQueryChanged = { viewModel.onEvent(AddResultsEvent.SearchResult(it)) },
        onItemClicked = {
            navController.navigate(
                Screen.EditResultScreen.withArgs(
                    competitionId.toString(), disciplineId.toString(), it.result.id.toString()
                )
            )
        },
        error = when (viewModel.saveResultState) {
            SaveSportResultResult.CompetitorDoesNotExist ->
                stringResource(R.string.competitor_does_not_exist)

            SaveSportResultResult.ResultAlreadyExists -> {
                stringResource(R.string.result_already_exists)
            }

            else -> null
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultsScreen(
    uiState: AddResultsUiState,
    searchQuery: String,
    results: List<SportResultAndCompetitor>,
    onSearchQueryChanged: (String) -> Unit = {},
    onEvent: (AddResultsEvent) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onItemClicked: (SportResultAndCompetitor) -> Unit = {},
    error: String? = null,
) {

    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        topBar = {
            TopAppBar(
                title = {},
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight(),
        ) {

            when (uiState) {
                AddResultsUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                is AddResultsUiState.Loaded -> {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.discipline.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.height(MaterialTheme.spacing.medium))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    ) {
                        items(results.sortedBy { it.result.timeStamp }) {
                            ResultItem(
                                pointType = uiState.discipline.type,
                                resultWithCompetitor = it,
                                onLongClick = onItemClicked
                            )
                        }
                    }

                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                    if (error != null) {
                        Text(error, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                        ResultInputField(
                            hasError = error != null,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            result = uiState.result,
                            number = uiState.number,
                            pointType = uiState.discipline.type,
                            onResultChange = { onEvent(AddResultsEvent.UpdateResult(it)) },
                            onNumberChanged = { onEvent(AddResultsEvent.UpdateNumber(it)) },
                            onDone = { onEvent(AddResultsEvent.SaveResult) }
                        )

                        val containerColor =
                            if (error != null) MaterialTheme.colorScheme.errorContainer
                            else MaterialTheme.colorScheme.tertiaryContainer
                        Spacer(Modifier.width(MaterialTheme.spacing.small))
                        AddButton(
                            onClick = { onEvent(AddResultsEvent.SaveResult) },
                            containerColor = containerColor,
                        )
                    }

                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewAddResultsScreen() {
    var state by remember {
        mutableStateOf(
            AddResultsUiState.Loaded(
                SubDiscipline(
                    0,
                    "Прыжки с крыши",
                    0,
                    DisciplinePointType.LONG_TIME
                ),
                0, 0,
            )
        )
    }

    AddResultsScreen(
        uiState = state,
        onEvent = {
            if (it is AddResultsEvent.UpdateResult) {
                state = state.copy(result = it.value)
            } else if (it is AddResultsEvent.UpdateNumber) {
                state = state.copy(number = it.value)
            }
        },
        error = "Участник с таким номером не существует в соревновании",
        results = (1..4).map {
            SportResultAndCompetitor(
                competitor = Competitor(
                    id = 0,
                    number = it,
                    competitionId = 0,
                    name = "Да",
                    gender = Gender.MALE,
                    teamName = "да",
                    degree = 10,
                ),
                result = SportResult(
                    disciplineId = 0,
                    competitionId = 0,
                    competitorId = 0,
                    value = it * 10
                )
            )

        }.toList(),
        searchQuery = "",
    )
}