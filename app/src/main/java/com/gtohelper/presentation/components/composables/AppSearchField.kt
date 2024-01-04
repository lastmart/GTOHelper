package com.gtohelper.presentation.components.composables


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.R
import com.gtohelper.presentation.ui.theme.AppBorderColor

@Preview
@Composable
fun PreviewAppSearchField() {
    AppSearchField(
        hint = "Search",
    )
}


@Composable
fun AppSearchField(
    value: String = "",
    hint: String? = "",
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        shape = RoundedCornerShape(10.dp),
        placeholder = { hint?.let { Text(it) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppBorderColor,
            unfocusedBorderColor = AppBorderColor,
        ),
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_gray_24),
                contentDescription = "Search"
            )
        },
    )
}