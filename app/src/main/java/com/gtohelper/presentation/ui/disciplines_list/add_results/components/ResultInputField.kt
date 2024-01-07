package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.input_fields.LongTimeInputField
import com.gtohelper.presentation.components.transformations.VisibleHintTransformation

@Preview
@Composable
fun Preview() {
    val result by remember { mutableIntStateOf(3800) }
    val number by remember { mutableIntStateOf(10) }
    ResultInputField(
        result = result,
        number = number,
        disciplinePointType = DisciplinePointType.AMOUNT,
    )
}


@Composable
fun ResultInputField(
    modifier: Modifier = Modifier,
    result: Int,
    number: Int,
    disciplinePointType: DisciplinePointType,
    onValueChange: (Int) -> Unit = {},
    onAddClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
//                AppTextField(
//                    modifier = Modifier.weight(1f),
//                    textStyle = TextStyle(textAlign = TextAlign.Center),
//                    value = number.toString(),
//                    onValueChange = {},
//                )
                val textStyle = TextStyle(fontSize = 25.sp)
                BasicTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = if (number == 0) "" else number.toString(),
                    onValueChange = { v ->
                        if (v.length <= 2) {
                            val number = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                            if (number in 0..23) {
                                
                            }
                        }
                    },
                    textStyle = textStyle,
                    visualTransformation = VisibleHintTransformation("00"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                var hours by remember { mutableIntStateOf(10) }
                var minutes by remember { mutableIntStateOf(10) }
                var seconds by remember { mutableIntStateOf(12) }
                LongTimeInputField(
                    modifier = Modifier
                        .weight(2f),
                    seconds = seconds,
                    minutes = minutes,
                    hours = hours,
                    onSecondsChanged = {
                        seconds = it
                    },
                    onMinutesChanged = {
                        minutes = it
                    },
                    onHoursChanged = {
                        hours = it
                    }
                )

//                AppTextField(
//                    modifier = Modifier
//                        .weight(2f),
//                    textStyle = TextStyle(textAlign = TextAlign.Center),
//                    value = result.toString(),
//                    onValueChange = {},

            }
        }

        AddButton()
    }
}