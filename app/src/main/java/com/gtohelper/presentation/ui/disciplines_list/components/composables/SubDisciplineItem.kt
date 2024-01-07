package com.gtohelper.presentation.ui.disciplines_list.components.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.R
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubDisciplineItem(
    subDiscipline: SubDiscipline,
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    onClick: (SubDiscipline) -> Unit,
    onLongClick: (SubDiscipline) -> Boolean,
    textOverflow: TextOverflow = TextOverflow.Clip,
    textFontSize: TextUnit = 18.sp,
    textMaxLines: Int = 2
) {
    var modifier = modifier
    var fontSize by remember { mutableStateOf(textFontSize) }
//    var isReadyToDraw by remember { mutableStateOf(false) }

    if (isClickable) {
        modifier = modifier.combinedClickable(
            onClick = { onClick(subDiscipline) },
            onLongClick = { onLongClick(subDiscipline) }
        )
    }

    Row(
        modifier = modifier
        //    .drawWithContent {
        //        if (isReadyToDraw) {
        //            drawContent()
        //        }
        //    }
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = subDiscipline.imageResource),
            contentDescription = subDiscipline.name,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = subDiscipline.name,
            fontSize = fontSize,
            overflow = textOverflow,
            maxLines = textMaxLines,
          //  onTextLayout = { textLayoutResult ->
          //      if (textLayoutResult.lineCount >= 2 && fontSize == textFontSize) {
          //          fontSize = 18.sp
          //      } else {
          //          isReadyToDraw = true
          //      }
          //  }
        )
    }
}


@Preview
@Composable
fun SubDisciplineItemPreview() {
    SubDisciplineItem(
        subDiscipline = SubDiscipline(
            imageResource = R.drawable.sub_discipline_sprinting_30m,
            name = "Бег на 30 м",
            type = DisciplinePointType.TIME
        ),
        onClick = {},
        onLongClick = { false },
        textFontSize = 25.sp
    )
}