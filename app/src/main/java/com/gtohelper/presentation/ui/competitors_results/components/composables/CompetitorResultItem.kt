package com.gtohelper.presentation.ui.competitors_results.components.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitorResultItem(
    competitor: Competitor,
    resultPoints: Int,
    onClick: (Pair<Competitor, Int>) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick(competitor to resultPoints) }
    ) {

        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_person),
                contentDescription = null
            )

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row {
                    Text(
                        text = competitor.number.toString(),
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = competitor.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "Команда: ${competitor.teamName}",
                    fontSize = 14.sp,
                )

                Text(
                    text = "Ступень: ${competitor.degree}",
                    fontSize = 14.sp,
                )

                val gender = when (competitor.gender) {
                    Gender.MALE -> "М"
                    Gender.FEMALE -> "Ж"
                }

                Text(
                    text = "Пол: $gender",
                    fontSize = 14.sp,
                )
            }

            Row(
                modifier = Modifier.weight(0.25f, fill = false),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(
                    modifier = Modifier
                        .height(68.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                Spacer(modifier = Modifier.width(5.dp))


                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.padding(end = 5.dp),
                        maxLines = 1,
                        text = resultPoints.toString(),
                        fontSize = 20.sp
                    )

                }


            }
        }
    }
}

@Preview
@Composable
fun CompetitorResultItemPreview() {
    CompetitorResultItem(
        competitor = Competitor(
            id = 7,
            number = 31,
            competitionId = 2,
            name = "Хрусталев Дмитрий Романович",
            gender = Gender.MALE,
            teamName = "Кроссфит",
            degree = 6
        ),
        resultPoints = 342
    )
}