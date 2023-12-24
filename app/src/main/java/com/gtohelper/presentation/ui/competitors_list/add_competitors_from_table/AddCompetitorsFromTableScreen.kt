package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

//import android.net.Uri
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.navigation.NavController
//import com.darkrockstudios.libraries.mpfilepicker.FilePicker
//import java.io.File


//@Composable
//fun AddCompetitorsFromTableScreen(
//    viewModel: AddCompetitorsFromTableViewModel,
//    navController: NavController,
//) {
//    var showFilePicker by remember { mutableStateOf(true) }
//    val fileType = listOf("xlsx")
//    FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
//        showFilePicker = false
//        val uri = Uri.parse(file?.path)
//        val fileAndroid = File(uri.path.toString());
//        val content = fileAndroid.readText()
//        println()
//        val documentFile = DocumentFile.fromSingleUri(, f)
//        file?.path?.let { viewModel.addFromExcelTable(it) }
//    }
//}