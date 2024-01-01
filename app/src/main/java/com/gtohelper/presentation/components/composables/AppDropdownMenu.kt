package com.gtohelper.presentation.components.composables


import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.gtohelper.presentation.ui.util.toRoman


@Preview
@Composable
fun PreviewCustomDropdownMenu() {
    AppDropdownMenu(
        selected = 1,
        values = (1..11).toList(),
        onValueChanged = {},
        label = "Ступень участника",
        stringTransform = { value -> value.toRoman()?.let { "$it ступень" } ?: "-" }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdownMenu(
    selected: T,
    values: List<T>,
    onValueChanged: (T) -> Unit,
    stringTransform: (T) -> String,
    label: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = Modifier.background(Color(android.graphics.Color.parseColor("#F3F3F3"))),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {

        AppTextField(
            modifier = Modifier.menuAnchor().background(Color(android.graphics.Color.parseColor("#F3F3F3"))),
            value = stringTransform(selected),
            onValueChange = {},
            label = label,
            readOnly = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
        )

        ExposedDropdownMenu(
            modifier = Modifier.background(Color(android.graphics.Color.parseColor("#F3F3F3"))),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            values.map { value ->
                DropdownMenuItem(
                    text = { Text(stringTransform(value)) },
                    onClick = {
                        onValueChanged(value)
                        expanded = false
                    },
                )
            }
        }
    }
}