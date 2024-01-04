package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SportResult
import com.gtohelper.presentation.ui.theme.AppBorderColor
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun ResultItem(
    pointType: DisciplinePointType,
    result: SportResult,
) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppBorderColor),
    ) {
        Row {
            Text(
                modifier=Modifier.padding(MaterialTheme.spacing.medium),
                text = result.competitorNumber.toString()
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier=Modifier.padding(MaterialTheme.spacing.medium),
                text = result.value.toString()
            )
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    val result = SportResult(
        0, "", 0, 0, 1000
    )
    ResultItem(
        pointType = DisciplinePointType.TIME,
        result = result,
    )
}