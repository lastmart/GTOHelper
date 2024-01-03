package com.gtohelper.presentation.ui.disciplines_list.add_discipline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AddDisciplineRoute(
    navController: NavController,
    viewModel: AddDisciplineViewModel
) {
  //  val uiState by viewModel.

  //  AddDisciplineScreen(uiState = )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDisciplineScreen(
    uiState: AddDisciplineUIState
) {


    Scaffold(
        topBar = { TopAppBar(title = { Text("Добавить дисциплину") }) }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)){

        }
    }
}


@Preview
@Composable
fun AddDisciplineScreenPreview() {

}