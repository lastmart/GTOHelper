package com.gtohelper.presentation.ui.disciplines_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.components.composables.AppAlertDialogRoute
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.navigation.Screen
import com.gtohelper.presentation.ui.disciplines_list.components.composables.DisciplineCardItem
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun DisciplineListRoute(
    navController: NavController,
    viewModel: DisciplinesListViewModel,
    competitionId: Int,
    competitionName: String
) {
    val uiState by viewModel.uiState.collectAsState()
    DisciplinesListScreen(
        uiState = uiState,
        competitionName = competitionName,
        onBackClicked = { navController.navigateUp() },
        onItemClicked = {
              navController.navigate(
                  Screen.AddResultsScreen.withArgs(competitionId.toString(), it.name)
              )
        },
        onAddButtonClicked = {
            navController.navigate(
                Screen.AddDisciplineScreen.withArgs(competitionId.toString())
            )
        },
        onItemLongClicked = {
            viewModel.onDisciplineLongPressed(it)
            true
        },
        onDownloadClicked = {},
        onResultsClicked = {
            navController.navigate(
                Screen.CompetitorsResultsListScreen.withArgs(competitionId.toString())
            )
        },
        onDescriptionClicked = {},
        onDeleteClicked = {
            viewModel.onDeleteCompetitionPressed()
        },
        onCompetitorsClicked = {
            navController.navigate(
                Screen.CompetitorsListScreen.withArgs(competitionId.toString())
            )
        }
    )

    if (viewModel.isDeleteDisciplineDialogShown) {
        AppAlertDialogRoute(
            title = "Удалить дисциплину",
            description = "Вы действительно хотите удалить\nэту дисциплину?",
            onOKClicked = {
                viewModel.onDismissDeleteDisciplineDialog()
                viewModel.deleteDiscipline()
            },
            onCancelClicked = { viewModel.onDismissDeleteDisciplineDialog() }
        )
    }

    if (viewModel.isDeleteCompetitionDialogShown) {
        AppAlertDialogRoute(
            title = "Удалить соревнование",
            description = "Вы действительно хотите удалить\nэто соревнование?",
            onOKClicked = {
                viewModel.onDismissDeleteCompetitionDialog()
                viewModel.deleteCompetition()
                navController.navigateUp()
            },
            onCancelClicked = { viewModel.onDismissDeleteCompetitionDialog() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisciplinesListScreen(
    competitionName: String,
    uiState: DisciplinesListUIState,
    onBackClicked: () -> Unit,
    onItemClicked: (DisciplinePresentation) -> Unit,
    onItemLongClicked: (DisciplinePresentation) -> Boolean,
    onAddButtonClicked: () -> Unit,
    onCompetitorsClicked: () -> Unit,
    onResultsClicked: () -> Unit,
    onDescriptionClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onDownloadClicked: () -> Unit
) {
    var isMenuExtended by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isMenuExtended = false
                        onCompetitorsClicked()
                    }) {
                        Icon(Icons.Outlined.Person, contentDescription = "Участники")
                    }

                    IconButton(onClick = {
                        isMenuExtended = false
                        onResultsClicked()
                    }) {
                        Icon(Icons.Outlined.List, contentDescription = "Результаты")
                    }

                    IconButton(onClick = { isMenuExtended = true }) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = "Ещё")
                    }

                    DropdownMenu(
                        expanded = isMenuExtended,
                        onDismissRequest = { isMenuExtended = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Описание") },
                            onClick = {
                                isMenuExtended = false
                                onDescriptionClicked()
                            }
                        )

                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.CenterHorizontally)
                        )

                        DropdownMenuItem(
                            text = { Text(text = "Удалить") },
                            onClick = {
                                isMenuExtended = false
                                onDeleteClicked()
                            }
                        )

                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.CenterHorizontally)
                        )

                        DropdownMenuItem(
                            text = { Text(text = "Скачать") },
                            onClick = {
                                isMenuExtended = false
                                onDownloadClicked()
                            }
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
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = competitionName,
                fontSize = 35.sp,
                color = Color.Black
            )

            Spacer(Modifier.height(15.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                items(uiState.disciplines) {
                    DisciplineCardItem(
                        discipline = it,
                        onClick = onItemClicked,
                        onLongClick = onItemLongClicked
                    )
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
                    imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                    name = "Бег на 1 км",
                    subDisciplines = listOf(),
                    type = DisciplinePointType.TIME,
                ),
                DisciplinePresentation(
                    imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                    name = "Бег на 2 км",
                    subDisciplines = listOf(),
                    type = DisciplinePointType.TIME,
                ),
            )
        ),
        competitionName = "МГУ",
        onItemClicked = {},
        onBackClicked = {},
        onAddButtonClicked = {},
        onItemLongClicked = { true },
        onCompetitorsClicked = {},
        onDeleteClicked = {},
        onDescriptionClicked = {},
        onDownloadClicked = {},
        onResultsClicked = {}
    )
}


