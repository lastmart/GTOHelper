package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.theme.AppBorderColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisciplineCardItem(
    discipline: DisciplinePresentation,
    onClick: (DisciplinePresentation) -> Unit,
    onLongClick: (DisciplinePresentation) -> Boolean,
    textFontSize: TextUnit = 20.sp
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(discipline) },
                onLongClick = { onLongClick(discipline) }
            ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = AppBorderColor),
    ) {
        DisciplineItem(
            discipline = discipline,
            onClick = onClick,
            onLongClick = onLongClick,
            textFontSize = textFontSize
        )
    }
}


@Preview
@Composable
fun DisciplineCardItemPreview() {
    DisciplineCardItem(
        discipline = DisciplinePresentation(
            imageResource = R.drawable.sub_discipline_long_distance_running_1km,
            name = "Бег на 1 км",
            subDisciplines = listOf(),
            type = DisciplinePointType.TIME
        ),
        onClick = {},
        onLongClick = { true }
    )
}