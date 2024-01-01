package com.gtohelper.presentation.ui.competitors_list.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.theme.AppBorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorItem(
    competitor: Competitor,
    onDeleteClick: (Competitor) -> Unit = {},
    onEditClick: (Competitor) -> Unit = {},
) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppBorderColor),
        onClick = { onEditClick(competitor) },
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.icon_person),
                    contentDescription = null
                )
                Column {
                    Text(competitor.number.toString() + ' ' + competitor.name)
                    Text(competitor.teamName)
                    Text(competitor.degree.toString())
                    Text(if (competitor.gender == Gender.MALE) "М" else "Ж")
                }
            }

            Row {
                Icon(
                    painter = painterResource(R.drawable.icon_edit),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Редактировать",
                )
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(R.drawable.icon_delete),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Удалить",
                )
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
            "Name",
            Gender.MALE,
            "Team name",
            12
        )
    )
}