package com.gtohelper.presentation.components.composables.menus

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.ui.competitions.components.CompetitionItem

data class DropDownItem(
    val text: String,
    val onClick: () -> Unit,
)

@Preview
@Composable
fun PreviewContextMenu() {
    LazyColumn {
        items(10) {
            ContextMenu(
                dropdownItems = listOf(
                    DropDownItem(text = "Some item", onClick = {

                    }),
                ),
            ) {
                CompetitionItem(
                    Competition(0, "asd", "asd")
                )
            }
        }
    }
}

@Composable
fun ContextMenu(
    dropdownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }

    val density = LocalDensity.current

    Card(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                    )
                }
        ) {
            content()
        }

        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight,
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(
                    onClick = {
                        it.onClick()
                        isContextMenuVisible = false
                    },
                    text = { Text(it.text) }
                )
            }
        }
    }
}