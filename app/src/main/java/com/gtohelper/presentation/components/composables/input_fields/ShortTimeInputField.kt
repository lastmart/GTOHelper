package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.ShortDuration
import com.gtohelper.presentation.components.transformations.MirroringVisibleHintTransformation

@Preview
@Composable
fun PreviewTimeInputField() {

    var value by remember {
        mutableStateOf(
            ShortDuration(
                seconds = 10,
                deciSeconds = 1,
            )
        )
    }

    ShortTimeInputField(
        value = value,
        onChanged = {
            value = it
        },
    )
}

@Composable
fun ShortTimeInputField(
    modifier: Modifier = Modifier,
    value: ShortDuration,
    textStyle: TextStyle = TextStyle.Default,
    onChanged: (ShortDuration) -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            modifier=Modifier.width(IntrinsicSize.Min),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )


        Text(
            modifier=Modifier.width(IntrinsicSize.Min),
            text = ".",
            style = textStyle,
        )
        BasicTextField(
            modifier=Modifier.width(IntrinsicSize.Min),
            value = if (value.deciSeconds == 0) "" else value.deciSeconds.toString(),
            onValueChange = { v ->
                if (v.length <= 1) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..9) {
                        onChanged(value.copy(deciSeconds = number))
                    }
                }
            },
            textStyle = textStyle,
            visualTransformation = MirroringVisibleHintTransformation("0"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

