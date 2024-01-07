package com.gtohelper.presentation.components.composables.placeholder_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gtohelper.R

@Composable
fun LoadFailedScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(stringResource(R.string.load_failed))
    }
}

@Preview
@Composable
fun Preview(){
    LoadFailedScreen()
}