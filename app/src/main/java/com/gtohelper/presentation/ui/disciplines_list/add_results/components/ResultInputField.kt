package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.components.composables.AddButton
import com.gtohelper.presentation.components.composables.AppTextField

@Preview
@Composable
fun Preview() {

    ResultInputField(
        result = 3800,
        number = 1,
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
                AppTextField(
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    value = number.toString(),
                    onValueChange = {},
                )
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                )
                AppTextField(
                    modifier = Modifier
                        .weight(2f),
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    value = result.toString(),
                    onValueChange = {},
                )
            }
        }

        AddButton()
    }
}