package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Arrangement
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
    textStyle: TextStyle = TextStyle.Default,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
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
            visualTransformation = VisibleHintTransformation("№"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}