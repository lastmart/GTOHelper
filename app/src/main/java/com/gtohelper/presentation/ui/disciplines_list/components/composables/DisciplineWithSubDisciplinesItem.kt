package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation

@Composable
fun DisciplineWithSubDisciplinesItem(
    discipline: DisciplinePresentation,
    onClick: (SubDiscipline) -> Unit,
    onLongClick: (SubDiscipline) -> Boolean,
    onSubDisciplineClicked: (SubDiscipline) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(discipline.toSubDiscipline()) },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        SubDisciplineItem(
            subDiscipline = discipline.toSubDiscipline(),
            onClick = onClick,
            onLongClick = { false },
            textFontSize = 20.sp
        )

        val isExpanded = discipline.isExpanded
        if (!isExpanded) return@Card

        discipline.subDisciplines.forEach { subDiscipline ->
            SubDisciplineItem(
                subDiscipline = subDiscipline,
                onClick = { onSubDisciplineClicked(subDiscipline) },
                onLongClick = { false },
                textFontSize = 25.sp
            )
        }
    }
}


@Preview
@Composable
fun DisciplineWithSubDisciplinesItemPreview() {
    DisciplineWithSubDisciplinesItem(
        discipline = DisciplinePresentation(
            imageResource = R.drawable.discipline_sprinting,
            name = "Бег на короткие дистанции",
            subDisciplines = listOf(
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_sprinting_30m,
                    name = "Бег на 30 м",
                    type = DisciplinePointType.SHORT_TIME
                ),
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_sprinting_60m,
                    name = "Бег на 60 м",
                    type = DisciplinePointType.SHORT_TIME
                ),
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_sprinting_100m,
                    name = "Бег на 100 м",
                    type = DisciplinePointType.SHORT_TIME
                ),
            ),
            isExpanded = true,
            type = DisciplinePointType.SHORT_TIME
        ),
        onClick = {},
        onLongClick = { false },
        onSubDisciplineClicked = {}
    )
}