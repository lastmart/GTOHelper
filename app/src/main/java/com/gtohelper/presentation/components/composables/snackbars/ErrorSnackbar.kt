package com.gtohelper.presentation.components.composables.snackbars

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewErrorSnackbar() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(snackbarHostState){
        coroutineScope.launch {
            snackbarHostState.showSnackbar("asdasd")
        }
    }
//    ErrorSnackbar(
//        snackbarHostState = snackbarHostState,
//    )
}

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState
) {

    SnackbarHost(snackbarHostState) {
        Snackbar(
            snackbarData = it,
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}