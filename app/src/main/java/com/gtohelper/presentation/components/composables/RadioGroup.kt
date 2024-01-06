package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.ui.util.capitalize

@Preview
@Composable
fun PreviewRadioGroup() {
    AppRadioGroup(
        selectedValue = Gender.FEMALE,
        onChanged = {},
        values = Gender.entries,
        nameTransform = { value -> value.name.lowercase().capitalize() }
    )
}

@Composable
fun <T> AppRadioGroup(
    modifier: Modifier = Modifier,
    selectedValue: T,
    onChanged: (T) -> Unit,
    values: List<T>,
    nameTransform: (T) -> String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        values.forEach { value ->
            AppRadioButton(
                value = value,
                selected = selectedValue == value,
                nameTransform = nameTransform,
                onClick = onChanged,
            )
        }
    }
}