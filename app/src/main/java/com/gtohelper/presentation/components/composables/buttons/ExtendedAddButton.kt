package com.gtohelper.presentation.components.composables.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewExtendedAddButton() {
    ExtendedAddButton(
        text="Select file",
    )
}

@Composable
fun ExtendedAddButton(
    text: String,
    onClick: () -> Unit = {},
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, null) },
        text = { Text(text) },
    )
}