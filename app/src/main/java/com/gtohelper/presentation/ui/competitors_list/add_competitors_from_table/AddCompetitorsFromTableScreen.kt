package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AddCompetitorsFromTableRoute(
    navController: NavController,
    viewModel: AddCompetitorsFromTableViewModel,
) {
    AddCompetitorsFromTableScreen(
        onUriReceived = viewModel::addCompetitorsFromTable,
    )
}


@Composable
fun AddCompetitorsFromTableScreen(
    onUriReceived: (Uri) -> Unit = {},
) {
    var showFilePicker by remember { mutableStateOf(true) }
    val filters = arrayOf("*/*")
    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { it?.let(onUriReceived) }
    )

    LaunchedEffect(key1 = showFilePicker){
        if (showFilePicker){
            singleFilePickerLauncher.launch(filters)
            showFilePicker = false
        }
    }
}

@Preview
@Composable
fun Preview(){
    AddCompetitorsFromTableScreen()
}