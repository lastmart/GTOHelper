package com.gtohelper.presentation.components.composables.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AddButtonPreview() {
    AddButton()
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentDescription: String? = null,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = contentDescription,
        )
    }
}