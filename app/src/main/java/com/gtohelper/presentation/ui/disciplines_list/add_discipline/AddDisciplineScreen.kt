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
import com.gtohelper.R
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.ui.disciplines_list.components.composables.DisciplineWithSubDisciplinesItem
import com.gtohelper.presentation.ui.disciplines_list.components.composables.SubDisciplineCardItem
import com.gtohelper.presentation.ui.models.DisciplinePresentation

@Composable
fun AddDisciplineRoute(
    navController: NavController,
    viewModel: AddDisciplineViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    AddDisciplineScreen(
        uiState = uiState,
        onSubDisciplineClicked = {
            viewModel.addSubDiscipline(it)
            navController.navigateUp()
        },
        onBackClicked = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDisciplineScreen(
    uiState: AddDisciplineUIState,
    onSubDisciplineClicked: (SubDiscipline) -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Добавить дисциплину") }, navigationIcon = {
                IconButton(onClick = onBackClicked) {
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
        uiState = AddDisciplineUIState(
            previewDisciplines
        ),
        onSubDisciplineClicked = {},
    )
}


@Composable
fun CollapsableLazyColumn(
    modifier: Modifier = Modifier,
    disciplines: List<DisciplinePresentation>,
    onSubDisciplineClicked: (SubDiscipline) -> Unit
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
                if (discipline.subDisciplines.size == 1
                    && discipline.subDisciplines[0].name == discipline.name
                ) {
                    SubDisciplineCardItem(
                        subDiscipline = discipline.toSubDiscipline(),
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
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 30 м",
                type = DisciplinePointType.SHORT_TIME,
            ),
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 60 м",
                type = DisciplinePointType.SHORT_TIME
            ),
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_sprinting_30m,
                name = "Бег на 100 м",
                type = DisciplinePointType.SHORT_TIME
            ),
        ),
        isExpanded = true,
        type = DisciplinePointType.SHORT_TIME
    ),
    DisciplinePresentation(
        imageResource = R.drawable.discipline_long_distance_running,
        name = "Бег на длинные дистанции",
        subDisciplines = listOf(
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_long_distance_running_1km,
                name = "Бег на 1 км",
                type = DisciplinePointType.SHORT_TIME
            ),
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km,
                name = "Бег на 1.5 км",
                type = DisciplinePointType.SHORT_TIME
            ),
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_long_distance_running_2km,
                name = "Бег на 2 км",
                type = DisciplinePointType.SHORT_TIME
            ),
            SubDiscipline(
                imageResource = R.drawable.sub_discipline_long_distance_running_3km,
                name = "Бег на 3 км",
                type = DisciplinePointType.SHORT_TIME
            ),
        ),
        type = DisciplinePointType.SHORT_TIME
    )
)