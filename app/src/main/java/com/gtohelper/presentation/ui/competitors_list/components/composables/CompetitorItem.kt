package com.gtohelper.presentation.ui.competitors_list.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.ui.theme.BorderColor
import com.gtohelper.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorItem(
    modifier: Modifier = Modifier,
    competitor: Competitor,
    onClick: (Competitor) -> Unit = {},
) {
    Card(
        modifier=modifier,
        border = BorderStroke(width = 1.dp, color = BorderColor),
        onClick = { onClick(competitor) },
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.icon_person),
                    contentDescription = null
                )
                Spacer(Modifier.width(MaterialTheme.spacing.medium))
                Column {
                    Row {
                        Text(competitor.number.toString())
                        Spacer(Modifier.width(MaterialTheme.spacing.extraSmall))
                        Text(
                            text=competitor.name,
                            style = TextStyle(fontWeight = FontWeight.Bold))
                    }
                    Text(stringResource(R.string.team_name, competitor.teamName))
                    Text(stringResource(R.string.competitor_degree, competitor.degree))
                    Text(
                        stringResource(
                            R.string.competitor_gender,
                            if (competitor.gender == Gender.MALE) "М" else "Ж"
                        )
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCompetitorItem() {
    CompetitorItem(
        competitor = Competitor(
            1,
            0,
            0,
            "Бойко Владимир Петрович",
            Gender.MALE,
            "Челябинск",
            12
        )
    )
}