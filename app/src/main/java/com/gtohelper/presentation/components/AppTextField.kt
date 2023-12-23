package com.gtohelper.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AppTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    hint: String? = "",
    hasError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint.toString()) },
        isError = hasError,
    )

    if (hasError) {
        Text(
            text = errorMessage.toString(),
            color = Color.Red,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}