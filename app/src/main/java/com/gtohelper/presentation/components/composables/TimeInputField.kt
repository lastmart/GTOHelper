package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TimeInputField() {
    var hours by remember { mutableIntStateOf(0) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.requiredHeight(IntrinsicSize.Min)
    ) {
        TimePartTextField("HH", hours) { hours = it }
//        Text(text = ":", modifier = Modifier.padding(horizontal = 8.dp))
        TimePartTextField("MM", minutes) { minutes = it }
//        Text(text = ":", modifier = Modifier.padding(horizontal = 8.dp))
        TimePartTextField("SS", seconds) { seconds = it }
    }
}

@Composable
fun TimePartTextField(label: String, value: Int, onValueChange: (Int) -> Unit) {
    TextField(
        value = if (value < 10) "0$value" else "$value",
        onValueChange = { newValue ->
            if (newValue.length <= 2) {
                val newValueInt = newValue.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0
                onValueChange(newValueInt)
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Preview
@Composable
fun PreviewTimeInputField() {
    TimeInputField()
}