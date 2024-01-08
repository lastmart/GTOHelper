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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SportResultAndCompetitor

@Composable
fun ResultItem(
    pointType: DisciplinePointType,
    resultWithCompetitor: SportResultAndCompetitor,
) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Center)
            Text(
                modifier = Modifier
                    .weight(1f),
                text = resultWithCompetitor.competitor.number.toString(),
                style = textStyle,
            )
            Divider(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(3f),
                text = pointType.toReadable(resultWithCompetitor.result.value),
                style = textStyle,
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
            sportName = "asd",
            competitionId = 0,
            competitorId = 0,
            value = 100
        )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DisciplinePointType.entries.forEach {
            ResultItem(
                pointType = it,
                resultWithCompetitor = resultAndCompetitor,
            )
        }
    }
}