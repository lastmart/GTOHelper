package com.gtohelper.presentation.ui.competitions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.ui.theme.AppBorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionItem(
    competition: Competition,
    onClick: (Competition) -> Unit = {},
) {
    Card(modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = AppBorderColor),
        onClick = { onClick(competition) }) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = competition.id.toString() + '/' + competition.name, style = TextStyle(fontSize = 30.sp))
            Text(text = competition.description, style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Preview
@Composable
fun CompetitionItemPreview() {
    CompetitionItem(Competition(1, "11 'Ы'", "Сдача нормативов"))
}
