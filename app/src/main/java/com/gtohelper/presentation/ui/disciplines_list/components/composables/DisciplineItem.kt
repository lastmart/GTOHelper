package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.presentation.ui.models.DisciplinePresentation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisciplineItem(
    discipline: DisciplinePresentation,
    onClick: (DisciplinePresentation) -> Unit,
    onLongClick: (DisciplinePresentation) -> Boolean,
    textFontSize: TextUnit = 20.sp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .combinedClickable(
                onClick = { onClick(discipline) },
                onLongClick = { onLongClick(discipline) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = discipline.imageResource),
            contentDescription = discipline.name,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = discipline.name,
            fontSize = textFontSize,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun DisciplineItemPreview() {
    DisciplineItem(
        discipline = DisciplinePresentation(
            imageResource = R.drawable.sub_discipline_sprinting_30m,
            name = "Бег на 30 м",
            subDisciplines = listOf(),
            type = DisciplinePointType.TIME
        ),
        onClick = {},
        onLongClick = { false },
        textFontSize = 25.sp
    )
}