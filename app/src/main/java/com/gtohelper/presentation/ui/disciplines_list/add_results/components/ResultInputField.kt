package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.components.composables.AppTextField
import com.gtohelper.presentation.components.composables.TimeInputField

@Preview
@Composable
fun PreviewResultInputField() {
    Column {
        ResultInputField(
            result = 3800,
            disciplinePointType = DisciplinePointType.TIME,
        )
        Spacer(Modifier.height(20.dp))
        ResultInputField(
            result = 3800,
            disciplinePointType = DisciplinePointType.AMOUNT,
        )
    }
}


@Composable
fun ResultInputField(
    result: Int,
    disciplinePointType: DisciplinePointType,
    onValueChange: (Int) -> Unit = {},
) {

    when (disciplinePointType) {
        DisciplinePointType.TIME -> {
            TimeInputField()
        }

        DisciplinePointType.AMOUNT -> {
            AppTextField(
                value = result.toString(),
                onValueChange = { value -> onValueChange(value.toIntOrNull() ?: 0) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }

        DisciplinePointType.BINARY -> {

        }
    }
}