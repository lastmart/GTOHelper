package com.gtohelper.presentation.ui.competitors_list.edit_competitor

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.TransparentAddFab
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorUiForm
import kotlinx.coroutines.launch


@Composable
fun EditCompetitorScreen(
    navController: NavController,
    viewModel: EditCompetitorViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.formState.collect { event ->
            Log.i("ADD_COMPETITION_SCREEN", "Event happened: $event")
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color(android.graphics.Color.parseColor("#F3F3F3"))),
        floatingActionButton = {
            TransparentAddFab(
                contentDescription = stringResource(R.string.save_new_competitor),
                onClick = { viewModel.onEvent(CompetitorFormEvent.Submit) },
            )
        }) {
        CompetitorUiForm(
            form = form,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(it)
                .background(Color(android.graphics.Color.parseColor("#F3F3F3"))),
        )
    }
}