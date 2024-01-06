package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.ExtendedAddButton
import com.gtohelper.presentation.components.composables.InfoAlertDialog
import com.gtohelper.presentation.components.composables.LoadingScreen
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun AddCompetitorsFromTableRoute(
    navController: NavController,
    viewModel: AddCompetitorsFromTableViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    AddCompetitorsFromTableScreen(
        uiState = uiState,
//        supportedFormats = viewModel.supportedFormats.toTypedArray(),
        onUriReceived = viewModel::addCompetitorsFromTable,
        onBackClicked = navController::navigateUp,
        onSuccessOkClicked = navController::navigateUp,
        onFailedOkClicked = viewModel::setInitial,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompetitorsFromTableScreen(
    uiState: AddCompetitorsFromTableUiState,
    supportedFormats: Array<String> = arrayOf("*/*"),
    onUriReceived: (Uri) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onSuccessOkClicked: () -> Unit = {},
    onFailedOkClicked: () -> Unit = {},
) {
    var showFilePicker by remember { mutableStateOf(false) }
    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { it?.let(onUriReceived) }
    )

    LaunchedEffect(showFilePicker) {
        if (showFilePicker) {
            singleFilePickerLauncher.launch(supportedFormats)
            showFilePicker = false
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small
        ),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.adding_from_table_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedAddButton(
                onClick = { showFilePicker = true },
                text="Выбрать файл",
            )
        }

        ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
        ) {
            when (uiState) {
                is AddCompetitorsFromTableUiState.Failed -> {
                    InfoAlertDialog(
                        title = "Ошибка",
                        description = uiState.reason,
                        onOKClicked = onFailedOkClicked
                    )
                }

                AddCompetitorsFromTableUiState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                AddCompetitorsFromTableUiState.Success -> {
                    InfoAlertDialog(
                        title = "Успех!",
                        description = "Участники успешно добавлены",
                        onOKClicked = onSuccessOkClicked,
                    )
                }

                AddCompetitorsFromTableUiState.Initial -> {
                    Column {
                        Text("Формат таблицы обязан быть следующим: ")
                        Image(
                            modifier=Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.table_format),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    val state = AddCompetitorsFromTableUiState.Initial
    AddCompetitorsFromTableScreen(
        uiState = state,
    )
}