package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.ui.mappers.toDisciplinePresentation
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.theme.BorderColor

@Composable
fun DisciplineWithSubDisciplinesItem(
    discipline: DisciplinePresentation,
    onClick: (DisciplinePresentation) -> Unit,
    onLongClick: (DisciplinePresentation) -> Boolean,
    onSubDisciplineClicked: (DisciplinePresentation) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(discipline) },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = BorderColor),
    ) {
        DisciplineItem(
            discipline = discipline,
            onClick = onClick,
            onLongClick = { false },
            textFontSize = 20.sp
        )

        val isExpanded = discipline.isExpanded
        if (!isExpanded) return@Card

        discipline.subDisciplines.forEach { subDiscipline ->
            val subDisciplinePresentation = subDiscipline.toDisciplinePresentation()
            DisciplineItem(
                discipline = subDisciplinePresentation,
                onClick = { onSubDisciplineClicked(subDisciplinePresentation) },
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
            name = "Бег на короткие дистанции", subDisciplines = listOf(
                DisciplinePresentation(
                    imageResource = R.drawable.sub_discipline_sprinting_30m,
                    name = "Бег на 30 м",
                    subDisciplines = listOf(),
                    type = DisciplinePointType.TIME
                ),
                DisciplinePresentation(
                    imageResource = R.drawable.sub_discipline_sprinting_60m,
                    name = "Бег на 60 м",
                    subDisciplines = listOf(),
                    type = DisciplinePointType.TIME
                ),
                DisciplinePresentation(
                    imageResource = R.drawable.sub_discipline_sprinting_100m,
                    name = "Бег на 100 м",
                    subDisciplines = listOf(),
                    type = DisciplinePointType.TIME
                ),
            ),
            isExpanded = true,
            type = DisciplinePointType.TIME
        ),
        onClick = {},
        onLongClick = { false },
        onSubDisciplineClicked = {}
    )
}