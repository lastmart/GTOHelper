package com.gtohelper.presentation.ui.competitors_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.gtohelper.presentation.components.composables.input_fields.AppSearchField
import com.gtohelper.presentation.ui.competitors_results.components.composables.CompetitorResultItem


@Composable
fun CompetitorsResultsRoute(
    navController: NavController,
    viewModel: CompetitorsResultsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    CompetitorsResultsScreen(uiState = uiState, onBackClicked = { navController.navigateUp() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorsResultsScreen(
    uiState: CompetitorsResultsUIState, onBackClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.results_title),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
            }, navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Назад"
                    )
                }
            })
        },
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding)
        ) {

            AppSearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                hint = "Поиск результатов...",
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {

                items(items = uiState.competitorsResults) {
                    CompetitorResultItem(competitor = it.first, resultPoints = it.second)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }

        }
    }

}


@Preview
@Composable
fun CompetitorsResultsScreenPreview() {
    CompetitorsResultsScreen(
        uiState = CompetitorsResultsUIState(
            competitorsResults = listOf(
                Competitor(
                    id = 7,
                    number = 33,
                    competitionId = 2,
                    name = "Хрусталев Дмитрий Романович",
                    gender = Gender.MALE,
                    teamName = "Кроссфит",
                    degree = 6
                ) to 350,

                Competitor(
                    id = 8,
                    number = 31,
                    competitionId = 2,
                    name = "Хрусталев Дмитрий Романович",
                    gender = Gender.FEMALE,
                    teamName = "Гандбол",
                    degree = 7
                ) to 72,

                Competitor(
                    id = 9,
                    number = 32,
                    competitionId = 2,
                    name = "Хрусталев Дмитрий Романовичabcdefgh",
                    gender = Gender.MALE,
                    teamName = "Вьетнам",
                    degree = 8
                ) to 9,
            )
        )
    )
}