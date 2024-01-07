package com.gtohelper.presentation.components.composables.buttons

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun PreviewCollapsedAddButton() {
    var expanded by remember { mutableStateOf(true) }
    ExpandingAddButton(
        expanded = expanded,
        onClick = {
            expanded = !expanded
        },
        actions = {
            AddButton()
            AddButton()
        }
    )
}

@Composable
fun ExpandingAddButton(
    expanded: Boolean,
    onClick: () -> Unit = {},
    actions: @Composable ColumnScope.() -> Unit,
) {
    val transition = updateTransition(targetState = expanded, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it) 135f else 0f
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (expanded) {
            actions()
        }
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotate),
                imageVector = Icons.Filled.Add,
                contentDescription = null,
            )
        }
    }
}