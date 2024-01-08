package com.gtohelper.presentation.components.composables.input_fields

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gtohelper.presentation.components.transformations.VisibleHintTransformation
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Composable
fun ShortTimeInputField(
    decimalSeconds: Int,
    seconds: Int,
    onChanged: (Duration) -> Unit = {}
) {
    Row {
        TextField(
            value = seconds.toString() + decimalSeconds.toString(),
            onValueChange = { value ->
                val millis = value.toInt()
                if (millis in 0..599) onChanged(
                    (seconds * 1000 + decimalSeconds * 100).toDuration(
                        DurationUnit.MILLISECONDS,
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle()
        )
    }
}

@Preview
@Composable
fun PreviewTimeInputField() {

    var text by remember {
        mutableStateOf("")
    }

    BasicTextField(
        value = text,
        onValueChange = { value ->
            if (value.length <= 2){
                text = value.filter { it.isDigit() }
            }
        },
        textStyle = TextStyle(fontSize = 40.sp),
        visualTransformation = VisibleHintTransformation("00"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}