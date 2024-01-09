package com.gtohelper.presentation.ui.competitions.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gtohelper.domain.models.Competition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionItem(
    competition: Competition,
    onClick: (Competition) -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        onClick = { onClick(competition) })
    {

        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = competition.name,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (competition.description.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(3.dp))
            }

            Text(
                text = competition.description,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp,
                color = Color(0xFFCFEBC1).copy(alpha = 0.8f)
            )

        }
    }
}

@Preview
@Composable
fun CompetitionItemPreview() {
    CompetitionItem(
        Competition(
            1, "ааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааа",
            "Без описания"
        )
    )
}
