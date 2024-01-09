package com.gtohelper.presentation.ui.competitions.edit_competition

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.buttons.CheckButton
import com.gtohelper.presentation.components.composables.snackbars.ErrorSnackbarHost
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitions.components.forms.CompetitionForm
import com.gtohelper.presentation.ui.competitions.components.forms.CompetitionFormEvent
import com.gtohelper.presentation.ui.competitions.components.forms.CompetitionFormState
import kotlinx.coroutines.launch


@Composable
fun EditCompetitionRoute(
    navController: NavController,
    viewModel: EditCompetitionViewModel,
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(context) {
        viewModel.formState.collect { event ->
            if (event is FormState.FormSubmissionFailedState) {
                snackbarHostState.currentSnackbarData?.dismiss()
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(event.error)
                }
            }
            if (event is FormState.FormSubmittedState) {
                navController.navigateUp()
            }
        }
    }

    EditCompetitionScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onAddButtonClicked = { viewModel.onEvent(CompetitionFormEvent.SubmitForm) },
        onBackClick = navController::navigateUp,
        snackbarHostState = snackbarHostState,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCompetitionScreen(
    uiState: CompetitionFormState,
    onEvent: (CompetitionFormEvent) -> Unit = {},
    onAddButtonClicked: () -> Unit = {},
    onBackClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = { ErrorSnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.edit))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                },
            )
        },
        floatingActionButton = {
            CheckButton(
                onClick = onAddButtonClicked, contentDescription = null
            )
        },
    ) { padding ->
        CompetitionForm(
            modifier = Modifier.padding(padding),
            uiState = uiState,
            onEvent = onEvent,
        )
    }
}

@Preview
@Composable
fun PreviewEditCompetitionScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    EditCompetitionScreen(
        snackbarHostState = snackbarHostState,
        uiState = CompetitionFormState(),
        onEvent = {},
    )
}
