package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.presentation.components.transformations.VisibleHintTransformation


@Preview
@Composable
fun PreviewLongTimeInputField() {


    var hours by remember { mutableIntStateOf(10) }
    var minutes by remember { mutableIntStateOf(10) }
    var seconds by remember { mutableIntStateOf(12) }
    LongTimeInputField(
        seconds = seconds,
        minutes = minutes,
        hours = hours,
        onSecondsChanged = {
            seconds = it
        },
        onMinutesChanged = {
            minutes = it
        },
        onHoursChanged = {
            hours = it
        }
    )
}

@Composable
fun LongTimeInputField(
    modifier: Modifier = Modifier,
    seconds: Int,
    minutes: Int,
    hours: Int,
    onSecondsChanged: (Int) -> Unit = {},
    onMinutesChanged: (Int) -> Unit = {},
    onHoursChanged: (Int) -> Unit = {},
) {
    val textStyle = TextStyle(fontSize = 25.sp)
    Row(
        modifier=modifier,
    ) {
        BasicTextField(
            modifier = Modifier
                .width(IntrinsicSize.Min),
            value = if (hours == 0) "" else hours.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..23) {
                        onHoursChanged(number)
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = VisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Text(
            text = ":",
            style = textStyle,
        )
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (minutes == 0) "" else minutes.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..59) {
                        onMinutesChanged(number)
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = VisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Text(
            text = ":",
            style = textStyle,
        )
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (seconds == 0) "" else seconds.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..59) {
                        onSecondsChanged(number)
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = VisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}