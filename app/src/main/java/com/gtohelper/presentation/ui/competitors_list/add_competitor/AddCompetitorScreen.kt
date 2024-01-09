package com.gtohelper.presentation.ui.competitors_list.add_competitor


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorUiForm
import com.gtohelper.presentation.ui.theme.spacing
import kotlinx.coroutines.launch


@Composable
fun AddCompetitorRoute(
    navController: NavController,
    viewModel: AddCompetitorViewModel,
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

    val form by viewModel.form.collectAsState()

    AddCompetitorScreen(
        form = form,
        snackbarHostState = snackbarHostState,
        onBackClicked = navController::navigateUp,
        onEvent = viewModel::onEvent,
    )
}

@Preview
@Composable
fun Preview() {
    val snackbarHostState = remember { SnackbarHostState() }

    AddCompetitorScreen(
        form = CompetitorFormState(),
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompetitorScreen(
    form: CompetitorFormState,
    snackbarHostState: SnackbarHostState,
    onBackClicked: () -> Unit = {},
    onEvent: (CompetitorFormEvent) -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        snackbarHost = { ErrorSnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.new_competitor))
                },
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
        floatingActionButton = {
            CheckButton(
                contentDescription = stringResource(R.string.save_new_competitor),
                onClick = { onEvent(CompetitorFormEvent.Submit) },
            )
        }) {

        CompetitorUiForm(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            form = form,
            onEvent = onEvent,
        )
    }
}