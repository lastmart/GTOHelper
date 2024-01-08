package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubDisciplineCardItem(
    subDiscipline: SubDiscipline,
    modifier: Modifier = Modifier,
    onClick: (SubDiscipline) -> Unit,
    onLongClick: (SubDiscipline) -> Boolean,
    textFontSize: TextUnit = 18.sp
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(subDiscipline) },
                onLongClick = { onLongClick(subDiscipline) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        SubDisciplineItem(
            subDiscipline = subDiscipline,
            onClick = onClick,
            modifier = modifier,
            onLongClick = onLongClick,
            textFontSize = textFontSize
        )
    }
}


@Preview
@Composable
fun SubDisciplineCardItemPreview() {
    SubDisciplineCardItem(
        subDiscipline = SubDiscipline(
            imageResource = R.drawable.sub_discipline_long_distance_running_1km,
            name = "Бег на 1 км",
            type = DisciplinePointType.LONG_TIME
        ),
        onClick = {},
        onLongClick = { true }
    )
}