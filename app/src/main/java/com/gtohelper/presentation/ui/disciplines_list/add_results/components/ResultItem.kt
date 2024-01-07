package com.gtohelper.presentation.ui.disciplines_list.add_results.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.presentation.ui.theme.BorderColor
import com.gtohelper.presentation.ui.theme.spacing

@Composable
fun ResultItem(
    pointType: DisciplinePointType,
    resultWithCompetitor: SportResultAndCompetitor,
) {
    Card(
        border = BorderStroke(width = 1.dp, color = BorderColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .weight(1f),
                text = resultWithCompetitor.competitor.number.toString()
            )
            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .weight(2f),
                text = pointType.toReadable(resultWithCompetitor.result.value)
            )
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    val resultAndCompetitor = SportResultAndCompetitor(
        competitor = Competitor(
            id = 0,
            number = 1,
            competitionId = 0,
            name = "Да",
            gender = Gender.MALE,
            teamName = "да",
            degree = 10,
        ),
        result = SportResult(
            sportName = "Прыжки с крыши",
            competitionId = 0,
            competitorId = 0,
            value = 100
        )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ResultItem(
            pointType = DisciplinePointType.SHORT_TIME,
            resultWithCompetitor = resultAndCompetitor,
        )
        ResultItem(
            pointType = DisciplinePointType.AMOUNT,
            resultWithCompetitor = resultAndCompetitor,
        )
    }
}