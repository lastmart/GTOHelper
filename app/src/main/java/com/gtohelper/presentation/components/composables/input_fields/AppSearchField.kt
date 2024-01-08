package com.gtohelper.presentation.components.composables.input_fields


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.R

@Preview
@Composable
fun PreviewWithHint() {
    AppSearchField(
        hint = "Search",
    )
}

@Preview
@Composable
fun PreviewWithValue() {
    AppSearchField(
        value = "Соревнование",
    )
}


@Composable
fun AppSearchField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String? = "",
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        modifier = modifier,
        placeholder = {
            hint?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiaryContainer),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                tint = MaterialTheme.colorScheme.tertiaryContainer,
                painter = painterResource(R.drawable.search_gray_24),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
    )
}