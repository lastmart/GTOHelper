package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.ui.util.capitalize

@Preview
@Composable
fun PreviewRadioGroup() {
    RadioGroup(
        initialValue = Gender.FEMALE,
        onChanged = {},
        values = Gender.entries,
        nameTransform = { value -> value.name }
    )
}

@Composable
fun <T : Enum<T>> RadioGroup(
    initialValue: T,
    onChanged: (T) -> Unit,
    values: List<T>,
    nameTransform: (T) -> String,
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
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = nameTransform(value),
                )
            }
        }
    }
}