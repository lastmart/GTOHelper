package com.gtohelper.presentation.ui.disciplines_list

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toDomainSubDiscipline
import com.gtohelper.data.repository.convertIntToSportResult
import com.gtohelper.domain.WriteFinalExcelTable
import com.gtohelper.domain.mapNormDisciplineToOfficialDisciplines
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.dialogs.AppAlertDialogRoute
import com.gtohelper.presentation.navigation.Screen
import com.gtohelper.presentation.ui.disciplines_list.components.composables.SubDisciplineCardItem
import com.gtohelper.presentation.ui.theme.spacing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DisciplineListRoute(
    navController: NavController,
    viewModel: DisciplinesListViewModel,
    competitionId: Int,
    competitionName: String
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    val launcher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/vnd.ms-excel"),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult

            scope.launch(Dispatchers.IO) {

                val competitors = viewModel.getCompetitors()

                val disciplinesWithCompetitorsWithResults = viewModel
                    .getDisciplinesWithCompetitorsWithResults()
                    .filter { subDisciplineWithCompetitorsWithResults ->
                        subDisciplineWithCompetitorsWithResults.competitorsWithResults.isNotEmpty()
                    }
                    .map { subDisciplineWithCompetitorsWithResults ->

                        val competitionSelectedSubDisciplines =
                            subDisciplineWithCompetitorsWithResults.competitorsWithResults.filter {
                                it.competitor.competitionId == competitionId
                            }

                        if (competitionSelectedSubDisciplines.isEmpty()) {
                            return@map null
                        }

                        return@map subDisciplineWithCompetitorsWithResults.copy(
                            competitorsWithResults = competitionSelectedSubDisciplines
                        )

                    }.filterNotNull()

                var currentDiscipline: SubDiscipline? = null

                val sportResultsMap = disciplinesWithCompetitorsWithResults.associateBy(
                    keySelector = {
                        currentDiscipline = it.disciplineEntity.toDomainSubDiscipline()
                        mapNormDisciplineToOfficialDisciplines[it.disciplineEntity.name]!!
                    },
                    valueTransform = {
                        return@associateBy it.competitorsWithResults.associateBy(
                            keySelector = {
                                it.competitor.toDomainModel()
                            },
                            valueTransform = {
                                convertIntToSportResult(
                                    currentDiscipline!!, it.sportResult.toDomainModel()
                                )
                            }
                        )
                    }
                )

                val outputStream = context.contentResolver.openOutputStream(it)

                val finalWriter = WriteFinalExcelTable(context = context)

                finalWriter.createFinalTable(
                    nameTable = competitionName,
                    fileOutputStream = outputStream!!,
                    listCompetitor = competitors,
                    sportResults = sportResultsMap
                )

                outputStream.close()

                viewModel.onTableSaved()
            }

        }
    )


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(10.dp)
            )
        }
    ) { padding ->

        DisciplinesListScreen(
            uiState = uiState,
            competitionName = competitionName,
            modifier = Modifier.padding(padding),
            onBackClicked = { navController.navigateUp() },
            onItemClicked = {
                navController.navigate(
                    Screen.AddResultsScreen.withArgs(competitionId.toString(), it.id.toString())
                )
            },
            onAddButtonClicked = {
                navController.navigate(
                    Screen.AddDisciplineScreen.withArgs(competitionId.toString())
                )
            },
            onItemLongClicked = {
                viewModel.onSubDisciplineLongPressed(it)
                true
            },
            onDownloadClicked = {
                launcher.launch("результаты_${competitionName}.xls")
            },
            onResultsClicked = {
                navController.navigate(
                    Screen.CompetitorsResultsListScreen.withArgs(competitionId.toString())
                )
            },
            onDescriptionClicked = {
                navController.navigate(
                    Screen.EditCompetitionScreen.withArgs(competitionId.toString())
                )
            },
            onDeleteClicked = {
                viewModel.onDeleteCompetitionPressed()
            },
            onCompetitorsClicked = {
                navController.navigate(
                    Screen.CompetitorsListScreen.withArgs(competitionId.toString())
                )
            }
        )

        if (viewModel.isDeleteSubDisciplineDialogShown) {
            AppAlertDialogRoute(
                title = "Удалить дисциплину",
                description = "Вы действительно хотите удалить\nэту дисциплину?",
                onOKClicked = {
                    viewModel.onDismissDeleteSubDisciplineDialog()
                    viewModel.deleteSubDiscipline()
                },
                onCancelClicked = { viewModel.onDismissDeleteSubDisciplineDialog() }
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

        if (viewModel.isSnackBarShown) {
            println("Show snackbar....")

            LaunchedEffect(context) {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Таблица успешно сохранена",
                        duration = SnackbarDuration.Short
                    )
                }
                viewModel.onSnackBarDismiss()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisciplinesListScreen(
    competitionName: String,
    uiState: DisciplinesListUIState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onItemClicked: (SubDiscipline) -> Unit,
    onItemLongClicked: (SubDiscipline) -> Boolean,
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
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.icon_person),
                            contentDescription = "Участники"
                        )
                    }

                    IconButton(onClick = {
                        isMenuExtended = false
                        onResultsClicked()
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.icon_pedestal),
                            contentDescription = "Результаты"
                        )
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
            AddButton(
                onClick = { onAddButtonClicked() },
                contentDescription = "Добавить дисциплину"
            )
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = competitionName,
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )

            Spacer(Modifier.height(15.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                items(uiState.subDisciplines) {
                    SubDisciplineCardItem(
                        subDiscipline = it,
                        modifier = Modifier.fillMaxWidth(),
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
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                    name = "Бег на 1 км",
                    type = DisciplinePointType.LONG_TIME,
                ),
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                    name = "Бег на 2 км",
                    type = DisciplinePointType.LONG_TIME,
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


