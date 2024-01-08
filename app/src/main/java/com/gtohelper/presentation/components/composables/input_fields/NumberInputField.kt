package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
fun PreviewNumberInputField() {
    var number by remember { mutableIntStateOf(123) }
    NumberInputField(
        value = number,
        onChanged = {
            number = it
        }
    )
}


@Composable
fun NumberInputField(
    modifier: Modifier = Modifier,
    value: Int,
    onChanged: (Int) -> Unit = {},
) {
    val textStyle = TextStyle(fontSize = 25.sp)

    Box(
        modifier=modifier,
    ) {
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = if (value == 0) "" else value.toString(),
            onValueChange = { v ->
                if (v.length <= 3) {
                    val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                    if (number in 0..999) {
                        onChanged(number)
                    }
                }
            },
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}