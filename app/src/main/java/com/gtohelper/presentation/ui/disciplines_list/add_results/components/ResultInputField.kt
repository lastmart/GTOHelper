package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
import com.gtohelper.presentation.components.transformations.VisibleHintTransformation

@Preview
@Composable
fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        DisciplinePointType.entries.forEach {
            var number by remember { mutableIntStateOf(0) }
            var value by remember { mutableIntStateOf(61_000) }
            ResultInputField(result = value,
                number = number,
                hasError = true,
                pointType = it,
                onNumberChanged = { v1 -> number = v1 },
                onResultChange = { v2 -> value = v2 })
        }
    }
}


@Composable
fun ResultInputField(
    modifier: Modifier = Modifier,
    result: Int,
    number: Int,
    pointType: DisciplinePointType,
    onNumberChanged: (Int) -> Unit = {},
    onResultChange: (Int) -> Unit = {},
    onDone: () -> Unit = {},
    hasError: Boolean = false,
) {
    val contentColor = if (hasError) MaterialTheme.colorScheme.onErrorContainer
    else MaterialTheme.colorScheme.onTertiaryContainer

    val containerColor = if (hasError) MaterialTheme.colorScheme.errorContainer
    else MaterialTheme.colorScheme.tertiaryContainer

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            contentColor = contentColor,
            containerColor = containerColor,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(5.dp),
        ) {
            val textStyle = TextStyle(fontSize = 30.sp, color = contentColor)
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = if (number == 0) "" else number.toString(),
                onValueChange = { v ->
                    if (v.length <= 4) {
                        val value = v.filter { it.isDigit() }.toIntOrNull() ?: 0
                        if (value in 0..1000) {
                            onNumberChanged(value)
                        }
                    }
                },
                visualTransformation = VisibleHintTransformation("№"),
                textStyle = textStyle.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
            Divider(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .width(1.dp),
                color = contentColor,
            )
            val inputFieldModifier = Modifier.weight(2f)
            when (pointType) {
                DisciplinePointType.SHORT_TIME -> ShortTimeInputField(
                    textStyle = textStyle,
                    modifier = inputFieldModifier,
                    value = ShortDuration.fromMillis(result),
                    onChanged = { onResultChange(it.toMillis()) },
                )

                DisciplinePointType.LONG_TIME -> LongTimeInputField(
                    textStyle = textStyle,
                    modifier = inputFieldModifier,
                    value = LongDuration.fromMillis(result),
                    onChanged = { onResultChange(it.toMillis()) },
                    onDone = onDone,
                )

                DisciplinePointType.AMOUNT -> NumberInputField(
                    textStyle = textStyle,
                    modifier = inputFieldModifier,
                    value = result,
                    onChanged = onResultChange,
                    onDone = onDone,
                )
            }
        }
    }
}