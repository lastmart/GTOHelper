package com.gtohelper.presentation.ui.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HelpScreen() {
    // TODO: Maybe some JSON Objects representing Help Entries
    // Now hardcoded for simplicity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#F3F3F3")))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
    ) {
        HeaderText("Подготовка соревнования")

        Text("Как создать соревнование?")
        Text("Как добавить участников?")
        Text("Как редактировать список дисциплин?")
        Text("Как должна выглядеть импортируемая таблица?")

        HeaderText("Проведение соревнования")

        Text("Как добавить результат участника?")
        Text("Как изменить результат участника?")

        HeaderText("Подведение итогов соревнования")

        Text("Как скачать таблицу с результатами?")
        Text("Как посмотреть результаты?")

//        android:textSize="25dp"
//        android:textStyle="bold"
    }
}

@Composable
fun HeaderText(
    text: String,
) {
    Text(
        text,
        style = TextStyle(
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    )
}