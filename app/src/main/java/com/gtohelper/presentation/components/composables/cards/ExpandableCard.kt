package com.gtohelper.presentation.components.composables.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewCollapsed(){
    ExpandableCard(
        modifier = Modifier.fillMaxWidth(),
        title = "Как создать соревнование?",
        isInitiallyExpanded = false,
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Text(
                text = "На главном экране нужно нажать кнопку"
            )
        }
    }
}

@Preview
@Composable
fun PreviewExpanded() {
    ExpandableCard(
        modifier = Modifier.fillMaxWidth(),
        title = "Как создать соревнование?",
        isInitiallyExpanded = true,
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Text(
                text = "На главном экране нужно нажать кнопку "
            )
        }
    }
}

@Composable
fun ExpandableCardTitle(
    title: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = title,
        )
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    isInitiallyExpanded: Boolean = false,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(isInitiallyExpanded) }

    Card(
        modifier = modifier,
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        ExpandableCardTitle(
            modifier = Modifier.padding(10.dp),
            title = title,
            expanded = expanded,
        )

        if (expanded) {
            content()
        }
    }
}
