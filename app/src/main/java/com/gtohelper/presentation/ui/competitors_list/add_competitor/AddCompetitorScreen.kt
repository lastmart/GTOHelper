package com.gtohelper.presentation.ui.competitors_list.add_competitor


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorUiForm
import kotlinx.coroutines.launch


@Composable
fun AddCompetitorRoute(
    navController: NavController,
    viewModel: AddCompetitorViewModel,
    competitionId: Int,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompetitorScreen(
    form: CompetitorFormState,
    snackbarHostState: SnackbarHostState,
    onBackClicked: () -> Unit = {},
    onEvent: (CompetitorFormEvent) -> Unit = {},
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            TransparentAddFab(
                contentDescription = stringResource(R.string.save_new_competitor),
                onClick = { onEvent(CompetitorFormEvent.Submit) },
            )
        }) {

        CompetitorUiForm(
            form = form,
            onEvent = onEvent,
            modifier = Modifier.padding(it),
        )
    }
}

// TODO: Make it work?
//@Composable
//private fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            withContext(Dispatchers.Main.immediate) {
//                flow.collect(onEvent)
//            }
//        }
//    }
//}