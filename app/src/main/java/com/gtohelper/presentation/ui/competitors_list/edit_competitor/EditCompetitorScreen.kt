package com.gtohelper.presentation.ui.competitors_list.edit_competitor

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.gtohelper.presentation.components.composables.dialogs.AppAlertDialogRoute
import com.gtohelper.presentation.components.composables.buttons.CheckButton
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorUiForm
import com.gtohelper.presentation.ui.theme.spacing
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@Composable
fun EditCompetitorRoute(
    navController: NavController,
    viewModel: EditCompetitorViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
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

    LaunchedEffect(context) {
        viewModel.isDeleted.consumeAsFlow().collect { isDeleted ->
            if (isDeleted) {
                navController.navigateUp()
            }
        }
    }

    val form by viewModel.form.collectAsState()

    if (viewModel.isDeleteCompetitorDialogShown) {
        AppAlertDialogRoute(
            title = "Удалить участника",
            description = "Вы действительно хотите удалить\nэтого участника?",
            onOKClicked = {
                viewModel.onDismissDeleteCompetitorDialog()
                viewModel.deleteCompetitor()
            },
            onCancelClicked = { viewModel.onDismissDeleteCompetitorDialog() }
        )
    }

    EditCompetitorScreen(
        form = form,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        onBackClicked = navController::navigateUp,
        onDeleteClicked = viewModel::onDeleteClicked,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCompetitorScreen(
    form: CompetitorFormState,
    onEvent: (CompetitorFormEvent) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDeleteClicked) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.errorContainer,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            CheckButton(
                contentDescription = stringResource(R.string.edit_competitor),
                onClick = { onEvent(CompetitorFormEvent.Submit) },
            )
        }) {
        CompetitorUiForm(
            form = form,
            onEvent = onEvent,
            modifier = Modifier.padding(it)
        )
    }
}

@Preview
@Composable
fun Preview() {
    val snackbarHostState = remember { SnackbarHostState() }
    EditCompetitorScreen(
        form = CompetitorFormState(),
        snackbarHostState = snackbarHostState,
    )
}