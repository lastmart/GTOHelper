package com.gtohelper.presentation.ui.add_competition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.AppTextField
import com.gtohelper.presentation.components.composables.TransparentAddFab


@Composable
fun AddCompetitionRoute(
    navController: NavController,
    viewModel: AddCompetitionViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.created) {
        viewModel.created.collect { created ->
            if (created) {
                navController.navigateUp()
            }
        }
    }

    AddCompetitionScreen(
        uiState = uiState,
        onEvent =viewModel::onEvent,
        onAddButtonClicked = { viewModel.onEvent(AddCompetitionEvent.SubmitForm) },
        onBackClick=navController::navigateUp,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompetitionScreen(
    uiState: AddCompetitionUiState,
    onEvent: (AddCompetitionEvent) -> Unit = {},
    onAddButtonClicked: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.new_competition))
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
            TransparentAddFab(
                onClick = onAddButtonClicked, contentDescription = null
            )
        },
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding),
        ) {
            AppTextField(
                value = uiState.name,
                label = "Название",
                onValueChange = { onEvent(AddCompetitionEvent.UpdateName(it)) },
            )

            AppTextField(
                modifier = Modifier.fillMaxHeight(),
                value = uiState.description,
                label = "Описание",
                onValueChange = { onEvent(AddCompetitionEvent.UpdateDescription(it)) },
            )
        }
    }

}

@Preview
@Composable
fun PreviewAddCompetitionScreen() {
    AddCompetitionScreen(
        uiState = AddCompetitionUiState(),
        onEvent = {},
    )
}
