package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewAppTextField() {
    AppTextField(
        value = "Владимир",
        onValueChange = { },
        label = "Имя участника",
        maxLength = 10,
    )
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int? = null,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    colors: TextFieldColors = AppTextFieldDefaults.colors(),
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { v ->
            if (maxLength != null){
                if (v.length <= maxLength)
                    onValueChange(v)
            }
            else {
                onValueChange(v)
            }
        },
        singleLine = singleLine,
        textStyle = textStyle,
        readOnly = readOnly,
        shape = RectangleShape,
        maxLines = maxLines,
        label = { label?.let { Text(it) } },
        keyboardOptions = keyboardOptions,
        colors = colors,
    )
}