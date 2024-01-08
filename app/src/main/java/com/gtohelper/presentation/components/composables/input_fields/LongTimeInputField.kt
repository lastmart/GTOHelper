package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.LongDuration
import com.gtohelper.presentation.components.transformations.VisibleHintTransformation


@Preview
@Composable
fun PreviewLongTimeInputField() {
    var value by remember {
        mutableStateOf(
            LongDuration(
                seconds = 0,
                minutes =0,
                hours = 3,
            )
        )
    }

    LongTimeInputField(
        value = value,
        onDurationChanged = {
            value = it
        }
    )
}

@Composable
fun LongTimeInputField(
    modifier: Modifier = Modifier,
    value: LongDuration,
    onDurationChanged: (LongDuration) -> Unit,
) {
    val textStyle = TextStyle(fontSize = 25.sp)
    Row(
        modifier = modifier,
    ) {

        Box {
            BasicTextField(
                modifier = Modifier
                    .width(IntrinsicSize.Min),
                value = if (value.hours == 0) "" else value.hours.toString(),
                onValueChange = { v ->
                    if (v.length <= 2) {
                        val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                        if (number in 0..23) {
                            onDurationChanged(value.copy(hours = number))
                        }
                    }
                },
                textStyle = textStyle,
                visualTransformation = VisibleHintTransformation("00"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }

        Text(
            text = ":",
            style = textStyle,
        )
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (value.minutes == 0) "" else value.minutes.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..59) {
                        onDurationChanged(value.copy(minutes = number))
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
            value = if (value.seconds == 0) "" else value.seconds.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..59) {
                        onDurationChanged(value.copy(seconds = number))
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = VisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}