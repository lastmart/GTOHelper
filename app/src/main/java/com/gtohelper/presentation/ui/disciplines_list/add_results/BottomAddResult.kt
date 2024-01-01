package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.presentation.components.composables.AppTextFieldDefaults
import com.gtohelper.presentation.components.composables.TransparentAddFab

@Preview
@Composable
fun PreviewBottomAddResult() {
    BottomAddResult()
}


@Composable
fun BottomAddResult() {

    Row {
        CustomTextField(value = "Номер", onValueChange = {})
//        CustomTextField(value = "Результат", onValueChange = {})
        TransparentAddFab(onClick = { /*TODO*/ }, contentDescription = null)
    }

}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int? = null,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    TextField(
        modifier = modifier
            .background(Color.White),
        value = value,
        onValueChange = { v ->
            maxLength?.let {
                if (v.length <= maxLength)
                    onValueChange(v)
            } ?: run { onValueChange(v) }
        },
        textStyle = textStyle,
        readOnly = readOnly,
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
        colors = AppTextFieldDefaults.colors(),
    )
}