package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.theme.AppBorderColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisciplineItem(
    discipline: DisciplinePresentation,
    onClick: (DisciplinePresentation) -> Unit,
    onLongClick: (DisciplinePresentation) -> Boolean
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
        Row(
            modifier = Modifier.padding(5.dp),
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
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}


@Preview
@Composable
fun DisciplineItemPreview(

) {
    DisciplineItem(
        discipline = DisciplinePresentation(
            R.drawable.sub_discipline_long_distance_running_1km,
            "Бег на 1 км",
            listOf()
        ),
        onClick = {},
        onLongClick = { true }
    )
}