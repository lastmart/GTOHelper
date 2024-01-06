package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Gender

@Preview
@Composable
fun PreviewAppRadioButton() {
    AppRadioButton(
        selected = true,
        value = Gender.MALE,
        nameTransform = { it.name }
    )
}

@Composable
fun <T> AppRadioButton(
    modifier: Modifier = Modifier,
    value: T,
    selected: Boolean,
    nameTransform: (T) -> String,
    onClick: (T) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        RadioButton(
            modifier = Modifier
                .size(20.dp),
            selected = selected,
            onClick = { onClick(value) },
        )
        Spacer(Modifier.width(5.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = nameTransform(value),
        )
    }
}