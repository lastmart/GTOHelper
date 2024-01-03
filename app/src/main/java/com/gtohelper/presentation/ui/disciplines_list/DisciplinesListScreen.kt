package com.gtohelper.presentation.ui.disciplines_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.AppSearchField
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.ui.disciplines_list.components.composables.DisciplineItem
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.theme.spacing

@Composable
fun DisciplineListRoute(
    navController: NavController,
    viewModel: DisciplinesListViewModel,
    competitionId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    DisciplinesListScreen(
        navController = navController,
        uiState = uiState,
        onItemClicked = {},
        onAddButtonClicked = { navController.navigate("add_discipline/$competitionId") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisciplinesListScreen(
    navController: NavController,
    uiState: DisciplinesListUIState,
    onItemClicked: (DisciplinePresentation) -> Unit,
    onAddButtonClicked: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Дисциплины") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }

            )
        },
        floatingActionButton = {
            TransparentAddFab(
                onClick = { onAddButtonClicked() },
                contentDescription = "Добавить дисциплину"
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            //        AppSearchField(
            //            value = "123",
            //            hint = "Поиск участников...",
            //            onValueChange = {},
            //        )

            Spacer(Modifier.height(MaterialTheme.spacing.small))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                items(uiState.disciplines) {
                    DisciplineItem(discipline = it, onClick = {}, onLongClick = { false })
                }
            }


        }
    }
}


@Preview
@Composable
fun DisciplinesListScreenPreview() {
    DisciplinesListScreen(
        uiState = DisciplinesListUIState(
            listOf(
                DisciplinePresentation(
                    R.drawable.sub_discipline_long_distance_running_1km,
                    "Бег на 1 км",
                    listOf()
                ),
                DisciplinePresentation(
                    R.drawable.sub_discipline_long_distance_running_2km,
                    "Бег на 2 км",
                    listOf()
                ),
            )
        ),
        onItemClicked = {},
        onAddButtonClicked = {},
        navController = NavController(LocalContext.current)
    )
}


