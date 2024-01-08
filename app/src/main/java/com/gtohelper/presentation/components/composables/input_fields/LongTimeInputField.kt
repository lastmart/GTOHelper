package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.gtohelper.domain.models.LongDuration
import com.gtohelper.presentation.components.transformations.MirroringVisibleHintTransformation


@Preview
@Composable
fun PreviewLongTimeInputField() {
    var value by remember {
        mutableStateOf(
            LongDuration(
                seconds = 0,
                minutes = 0,
                hours = 3,
            )
        )
    }

    LongTimeInputField(
        value = value,
        onChanged = {
            value = it
        }
    )
}

@Composable
fun LongTimeInputField(
    modifier: Modifier = Modifier,
    value: LongDuration,
    textStyle: TextStyle = TextStyle.Default,
    onChanged: (LongDuration) -> Unit = {},
    onDone: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (value.hours == 0) "" else value.hours.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..23) {
                        onChanged(value.copy(hours = number))
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = MirroringVisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
        )


        Text(
            modifier = Modifier.width(IntrinsicSize.Min),
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
                        onChanged(value.copy(minutes = number))
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = MirroringVisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
        )
        Text(
            modifier = Modifier.width(IntrinsicSize.Min),
            text = ":",
            style = textStyle,
        )

        val actions =KeyboardActions(
            onDone = { onDone() },
            onNext = { }
        )

        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (value.seconds == 0) "" else value.seconds.toString(),
            onValueChange = { v ->
                if (v.length <= 2) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..59) {
                        onChanged(value.copy(seconds = number))
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = MirroringVisibleHintTransformation("00"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = actions
        )
    }
}