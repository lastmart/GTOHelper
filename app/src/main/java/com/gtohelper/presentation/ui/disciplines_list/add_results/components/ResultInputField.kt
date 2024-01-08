package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.LongDuration
import com.gtohelper.domain.models.ShortDuration
import com.gtohelper.presentation.components.composables.input_fields.LongTimeInputField
import com.gtohelper.presentation.components.composables.input_fields.NumberInputField
import com.gtohelper.presentation.components.composables.input_fields.ShortTimeInputField

@Preview
@Composable
fun Preview() {
    Column {
        DisciplinePointType.entries.forEach {
            var number by remember { mutableIntStateOf(10) }
            var value by remember { mutableIntStateOf(61_000) }
            ResultInputField(
                result = value,
                number = number,
                disciplinePointType = it,
                onNumberChanged = { v1 ->
                    number = v1
                },
                onResultChange = { v2 ->
                    value = v2
                }
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}


@Composable
fun ResultInputField(
    modifier: Modifier = Modifier,
    result: Int,
    number: Int,
    disciplinePointType: DisciplinePointType,
    onNumberChanged: (Int) -> Unit = {},
    onResultChange: (Int) -> Unit = {},
) {

    Card(
        modifier = modifier,
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

            val inputFieldModifier = Modifier
                .weight(2f)
            when (disciplinePointType) {
                DisciplinePointType.SHORT_TIME -> {
                    ShortTimeInputField(
                        modifier = inputFieldModifier,
                        value = ShortDuration.fromMillis(result),
                        onChanged = {
                            onResultChange(it.toMillis())
                        }
                    )
                }

                DisciplinePointType.LONG_TIME -> {
                    LongTimeInputField(
                        modifier = inputFieldModifier,
                        value = LongDuration.fromMillis(result),
                        onChanged = {
                            onResultChange(it.toMillis())
                        }
                    )
                }

                DisciplinePointType.AMOUNT -> {
                    NumberInputField(
                        modifier = inputFieldModifier,
                        value = result,
                        onChanged = onResultChange
                    )
                }
            }
        }
    }
}