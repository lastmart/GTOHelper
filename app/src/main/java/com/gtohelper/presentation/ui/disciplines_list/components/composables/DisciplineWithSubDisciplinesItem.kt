package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
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
    val isExpanded = discipline.isExpanded

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "arrow"
    )

    val enterTransition = expandVertically(expandFrom = Alignment.Top)
    val exitTransition = shrinkVertically()

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
        Row {
            val textMaxLines = if (isExpanded) 3 else 2

            SubDisciplineItem(
                subDiscipline = discipline.toSubDiscipline(),
                isClickable = false,
                modifier = Modifier.weight(1f),
                onClick = onClick,
                textOverflow = TextOverflow.Ellipsis,
                onLongClick = { false },
                textFontSize = 18.sp,
                textMaxLines = textMaxLines
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 5.dp)
                    .rotate(rotationState),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }


        discipline.subDisciplines.forEach { subDiscipline ->

            AnimatedVisibility(
                visible = isExpanded,
                enter = enterTransition,
                exit = exitTransition
            ) {
                SubDisciplineItem(
                    subDiscipline = subDiscipline,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSubDisciplineClicked(subDiscipline) },
                    onLongClick = { false },
                    textFontSize = 18.sp,
                )
            }

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
                    type = DisciplinePointType.TIME
                ),
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_sprinting_60m,
                    name = "Бег на 60 м",
                    type = DisciplinePointType.TIME
                ),
                SubDiscipline(
                    imageResource = R.drawable.sub_discipline_sprinting_100m,
                    name = "Бег на 100 м",
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