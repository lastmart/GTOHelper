package com.gtohelper.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.presentation.ui.util.capitalize

@Preview
@Composable
fun <T : Enum<T>> RadioGroup(
    initialValue: T,
    onChanged: (T) -> Unit,
    values: List<T>
) {
    val state = remember { mutableStateOf(initialValue) }

    Column {
        values.forEach { value ->
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = state.value == value,
                    onClick = {
                        state.value = value
                        onChanged(value)
                    },
                )
                Text(
                    text = value.name.lowercase().capitalize(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}