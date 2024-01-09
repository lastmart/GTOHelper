package com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.usecases.sport_result.EditSportResultResult
import com.gtohelper.presentation.components.composables.placeholder_screens.LoadingScreen
import com.gtohelper.presentation.ui.disciplines_list.add_results.components.ResultInputField

@Composable
fun EditResultDialogRoute(
    navController: NavController,
    viewModel: EditResultViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    EditResultDialog(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        editSportResultResult = viewModel.editResultState,
        onResultDeleted = navController::navigateUp,
        onDismissRequest = navController::navigateUp,
    )

    if (viewModel.editResultState is EditSportResultResult.Success) {
        navController.navigateUp()
    }
}

@Preview
@Composable
fun PreviewEditResultDialog() {

    val competitor = Competitor(
        id = 0,
        number = 4,
        competitionId = 0,
        name = "Да",
        gender = Gender.MALE,
        teamName = "да",
        degree = 10,
    )

    val result = SportResult(
        disciplineId = 0,
        competitionId = 0,
        competitorId = 0,
        value = 10000
    )

    EditResultDialog(
        uiState = EditResultUiState.Loaded(
            result = result,
            competitor = competitor,
            pointType = DisciplinePointType.AMOUNT,
        ),
        editSportResultResult = EditSportResultResult.ResultDoesNotExist,
    )
}


@Composable
fun EditResultDialog(
    uiState: EditResultUiState,
    onEvent: (EditResultEvent) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onResultDeleted: () -> Unit = {},
    editSportResultResult: EditSportResultResult? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        when (uiState) {
            EditResultUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())
            EditResultUiState.Deleted -> onResultDeleted()
            is EditResultUiState.Loaded -> {

                val error = when (editSportResultResult) {
                    EditSportResultResult.CompetitorDoesNotExistInCompetition ->
                        stringResource(R.string.competitor_does_not_exist)

                    EditSportResultResult.ResultAlreadyExists -> {
                        stringResource(R.string.result_already_exists)
                    }

                    else -> null
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(10.dp)) {
                        ResultInputField(
                            hasError = error != null,
                            modifier = Modifier,
//                            .align(Alignment.CenterVertically),
                            result = uiState.result.value,
                            number = uiState.competitor.number,
                            pointType = uiState.pointType,
                            onResultChange = { onEvent(EditResultEvent.UpdateResult(it)) },
                            onNumberChanged = { onEvent(EditResultEvent.UpdateNumber(it)) },
                            onDone = { onEvent(EditResultEvent.SubmitEdit) }
                        )
                        if (error != null) {
                            Text(error, color = MaterialTheme.colorScheme.errorContainer)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { onEvent(EditResultEvent.SubmitEdit) }) {
                                Text(
                                    text = stringResource(id = R.string.accept),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }

                            TextButton(onClick = { onEvent(EditResultEvent.SubmitDelete) }) {
                                Text(
                                    text = stringResource(id = R.string.delete),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.errorContainer
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}