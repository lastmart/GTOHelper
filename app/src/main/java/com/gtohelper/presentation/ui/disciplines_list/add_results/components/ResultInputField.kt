package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.LongDuration
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.input_fields.LongTimeInputField

@Preview
@Composable
fun Preview() {
    var number by remember { mutableIntStateOf(10) }
    var value by remember { mutableIntStateOf(61_000) }
    ResultInputField(
        result = value,
        number = number,
        disciplinePointType = DisciplinePointType.LONG_TIME,
        onNumberChanged = {
            number = it
        },
        onResultChange = {
            value = it
        }
    )
}


@Composable
fun ResultInputField(
    modifier: Modifier = Modifier,
    result: Int,
    number: Int,
    disciplinePointType: DisciplinePointType,
    onNumberChanged: (Int) -> Unit = {},
    onResultChange: (Int) -> Unit = {},
    onAddClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val textStyle = TextStyle(fontSize = 25.sp, textAlign = TextAlign.Center)
                BasicTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = if (number == 0) "" else number.toString(),
                    onValueChange = { v ->
                        if (v.length <= 4) {
                            val value = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                            if (value in 0..1000) {
                                onNumberChanged(value)
                            }
                        }
                    },
                    textStyle = textStyle,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                when (disciplinePointType) {
                    DisciplinePointType.SHORT_TIME -> {

                    }

                    DisciplinePointType.LONG_TIME -> {
                        LongTimeInputField(
                            modifier = Modifier
                                .weight(2f),
                            value = LongDuration.fromMillis(result),
                            onDurationChanged = {
                                onResultChange(it.toMillis())
                            }
                        )
                    }

                    DisciplinePointType.AMOUNT -> {

                    }
                }
            }
        }

        AddButton(onClick = onAddClicked)
    }
}