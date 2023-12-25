package com.gtohelper.presentation.components.composables

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewAppTextField() {
    AppTextField(
        value = "Владимир12322",
        onValueChange = { },
        label = "Имя участника",
        maxLength = 10,
    )
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        value = value,
        onValueChange = { v ->
            if(v.length <= maxLength)
//            {
                onValueChange(v)
//            } else {
//            }
//            focusManager.moveFocus(FocusDirection.Down)
        },
        isError = errorMessage != null,
        shape = RectangleShape,
        label = { label?.let { Text(it) } },
        keyboardOptions = keyboardOptions,
        supportingText = {
            maxLength?.let {
                Text(
                    color = Color.Black,
                    text = "${value.length} / $it",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledSupportingTextColor = Color.Transparent,
            errorSupportingTextColor = Color.Transparent,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent,
        ),
    )

    if (errorMessage != null) {
        Text(
            text = errorMessage.toString(),
            color = Color.Red,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}