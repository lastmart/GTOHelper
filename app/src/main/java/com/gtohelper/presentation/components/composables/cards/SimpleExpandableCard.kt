package com.gtohelper.presentation.components.composables.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewExpandableCard() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        listOf(true, false).forEach {
            var value by remember { mutableStateOf(it) }
            ExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как создать соревнование?",
                onClick = { value = !value },
                isExpanded = value,
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
    }
}

@Composable
fun SimpleExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(true) }

    ExpandableCard(
        modifier = modifier,
        title = title,
        isExpanded = expanded,
        onClick = { expanded = !expanded },
        content = content
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        ExpandableCardTitle(
            modifier = Modifier.padding(10.dp),
            title = title,
            expanded = isExpanded,
        )

        if (isExpanded) {
            content()
        }
    }
}

@Composable
internal fun ExpandableCardTitle(
    title: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {

    val rotationState by animateFloatAsState(targetValue = if (expanded) 90f else 0f, label = "")

    Row(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = title,
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .rotate(rotationState),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
        )
    }
}