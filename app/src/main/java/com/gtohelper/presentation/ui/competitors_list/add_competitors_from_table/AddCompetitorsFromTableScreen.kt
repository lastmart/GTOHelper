package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.LoadingScreen

@Composable
fun AddCompetitorsFromTableRoute(
    navController: NavController,
    viewModel: AddCompetitorsFromTableViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    AddCompetitorsFromTableScreen(
        uiState = uiState,
        onUriReceived = viewModel::addCompetitorsFromTable,
        onBackClicked = navController::navigateUp,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompetitorsFromTableScreen(
    uiState: AddCompetitorsFromTableUiState,
    onUriReceived: (Uri) -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    var showFilePicker by remember { mutableStateOf(true) }
    val filters = arrayOf("*/*")
    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { it?.let(onUriReceived) }
    )

    LaunchedEffect(key1 = showFilePicker) {
        if (showFilePicker) {
            singleFilePickerLauncher.launch(filters)
            showFilePicker = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.adding_from_table_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },

        ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
        ) {
            when (uiState) {
                AddCompetitorsFromTableUiState.Loading -> {
                    LoadingScreen()
                }

                AddCompetitorsFromTableUiState.Success -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column {
                            Icon(
                                modifier = Modifier
                                    .width(75.dp)
                                    .height(75.dp)
                                    .align(Alignment.CenterHorizontally),
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                            )
                            Text("Участники успешно добавлены")
                        }
                    }
                }

                is AddCompetitorsFromTableUiState.Failed -> {

                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(uiState.reason.toString())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    val state = AddCompetitorsFromTableUiState.Success
    AddCompetitorsFromTableScreen(
        uiState = state,
    )
}