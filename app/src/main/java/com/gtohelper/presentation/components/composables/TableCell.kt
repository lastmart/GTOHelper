package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float = 1f,
) {
    Text(
        text = text,
        modifier = Modifier
            .border(1.dp, Color.White)
            .weight(weight)
            .padding(8.dp)
    )
}