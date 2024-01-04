package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Gender

@Preview
@Composable
fun PreviewRadioGroup() {
    AppRadioGroup(
        selectedValue = Gender.FEMALE,
        onChanged = {},
        values = Gender.entries,
        nameTransform = { value -> value.name }
    )
}

@Composable
fun <T> AppRadioGroup(
    selectedValue: T,
    onChanged: (T) -> Unit,
    values: List<T>,
    nameTransform: (T) -> String,
) {
    val state = remember { mutableStateOf(selectedValue) }




    Row(

    ){
        values.forEach { value ->
            RadioButton(
                selected = state.value == value,
                onClick = {
                    state.value = value
                    onChanged(value)
                },
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically),
                text = nameTransform(value),
            )
        }
    }
}