package com.gtohelper.presentation.ui.disciplines_list.add_discipline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.ui.disciplines_list.components.composables.DisciplineCardItem
import com.gtohelper.presentation.ui.disciplines_list.components.composables.DisciplineWithSubDisciplinesItem
import com.gtohelper.presentation.ui.models.DisciplinePresentation

@Composable
fun AddDisciplineRoute(
    navController: NavController,
    viewModel: AddDisciplineViewModel,
    competitionId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    AddDisciplineScreen(
        navController = navController,
        uiState = uiState,
        onSubDisciplineClicked = {
            viewModel.addDiscipline(it)
            navController.navigateUp()
        },
        competitionId = competitionId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDisciplineScreen(
    navController: NavController,
    uiState: AddDisciplineUIState,
    onSubDisciplineClicked: (DisciplinePresentation) -> Unit,
    competitionId: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Добавить дисциплину") }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            })
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            CollapsableLazyColumn(
                disciplines = uiState.disciplines,
                onSubDisciplineClicked = onSubDisciplineClicked
            )
        }
    }
}


@Preview
@Composable
fun AddDisciplineScreenPreview() {
    AddDisciplineScreen(
        rememberNavController(),
        uiState = AddDisciplineUIState(
            previewDisciplines
        ),
        onSubDisciplineClicked = {},
        competitionId = 0
    )
}


@Composable
fun CollapsableLazyColumn(
    modifier: Modifier = Modifier,
    disciplines: List<DisciplinePresentation>,
    onSubDisciplineClicked: (DisciplinePresentation) -> Unit
) {
    val expandedState =
        remember(disciplines) { disciplines.map { it.isExpanded } }.toMutableStateList()


    LazyColumn(
        modifier = modifier
            .padding(horizontal = 15.dp)
    ) {
        disciplines.forEachIndexed { index, discipline ->
            val isExpanded = expandedState[index]

            item {
            //    if (discipline.subDisciplines.isEmpty()) {
            //        return@item
                    //    }

                if (discipline.subDisciplines.size == 1
                    && discipline.subDisciplines[0].name == discipline.name
                ) {
                    DisciplineCardItem(
                        discipline = discipline,
                        onClick = onSubDisciplineClicked,
                        onLongClick = { false }
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    return@item
                }

                DisciplineWithSubDisciplinesItem(
                    discipline = discipline,
                    onClick = {
                        expandedState[index] = !isExpanded
                        discipline.isExpanded = !isExpanded
                    },
                    onLongClick = { false },
                    onSubDisciplineClicked = onSubDisciplineClicked
                )

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Preview
@Composable
fun CollapsableLazyColumnPreview() {
    val disciplines = previewDisciplines.toList()
    CollapsableLazyColumn(disciplines = disciplines, onSubDisciplineClicked = {})
}

val previewDisciplines = listOf(
    DisciplinePresentation(
        imageResource = R.drawable.discipline_sprinting,
        name = "Бег на короткие дистанции",
        subDisciplines = listOf(
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 30 м",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME,
            ),
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 60 м",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 100 м",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
        ),
        isExpanded = true,
        type = DisciplinePointType.TIME
    ),
    DisciplinePresentation(
        imageResource = R.drawable.discipline_long_distance_running,
        name = "Бег на длинные дистанции",
        subDisciplines = listOf(
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                name = "Бег на 1 км",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
                name = "Бег на 1.5 км",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                name = "Бег на 2 км",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
            DisciplinePresentation(
                imageResource = R.drawable.sub_discipline_long_distance_running_3km,
                name = "Бег на 3 км",
                subDisciplines = listOf(),
                type = DisciplinePointType.TIME
            ),
        ),
        type = DisciplinePointType.TIME
    )
)