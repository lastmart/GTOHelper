package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import java.io.File

@Composable
fun AddCompetitorsFromTableRoute(
    navController: NavController,
    viewModel: AddCompetitorsFromTableViewModel,
) {
    AddCompetitorsFromTableScreen()
}


@Composable
fun AddCompetitorsFromTableScreen(

) {
    val context = LocalContext.current
    var showFilePicker by remember { mutableStateOf(true) }
    val filters = arrayOf("*/*")
    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
//            UriPathF
        })

    Button(onClick = {
        singleFilePickerLauncher.launch(filters)
    }) {
        Text(text = "Tap me")
    }
}