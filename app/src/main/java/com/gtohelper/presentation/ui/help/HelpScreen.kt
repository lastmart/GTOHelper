package com.gtohelper.presentation.ui.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gtohelper.R
import com.gtohelper.presentation.components.composables.buttons.AddButton
import com.gtohelper.presentation.components.composables.buttons.CheckButton
import com.gtohelper.presentation.components.composables.cards.SimpleExpandableCard
import com.gtohelper.presentation.ui.theme.spacing


@Composable
fun HelpRoute(
    navController: NavController,
) {
    HelpScreen(
        onBackClicked = navController::navigateUp,
    )
}

@Preview
@Composable
fun PreviewHelpScreen() {
    HelpScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    onBackClicked: () -> Unit = {},
) {
    val placeholder = Placeholder(
        width = 30.sp,
        height = 30.sp,
        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
    )

    val resultsIcon = Pair("results_icon", InlineTextContent(
        placeholder = placeholder,
        children = {
            Icon(
                painter = painterResource(R.drawable.icon_pedestal),
                contentDescription = null,
            )
        }
    ))

    val tripleDotIcon = Pair("triple_dot_icon", InlineTextContent(
        placeholder = Placeholder(
            width = 20.sp,
            height = 20.sp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
        ),
        children = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
            )
        }
    ))

    val pencilIcon = Pair("pencil_icon", InlineTextContent(
        placeholder = placeholder,
        children = {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
            )
        }
    ))

    val tableIcon = Pair("table_icon", InlineTextContent(
        placeholder = placeholder,
        children = {
            Icon(
                painter = painterResource(R.drawable.icon_table),
                contentDescription = null
            )
        }
    ))
    val checkIcon = Pair(
        "check_icon", InlineTextContent(
            placeholder = placeholder,
            children = { CheckButton() },
        )
    )

    val plusIcon = Pair("plus_icon", InlineTextContent(
        placeholder = placeholder,
        children = { AddButton() }
    ))

    val personIcon = Pair("person_icon", InlineTextContent(
        placeholder = placeholder,
        children = {
            Icon(painter = painterResource(R.drawable.icon_person), contentDescription = null)
        }
    ))

    Scaffold(
        contentWindowInsets = WindowInsets(
            left = MaterialTheme.spacing.small,
            right = MaterialTheme.spacing.small,
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.help_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
            )
        },
    ) { padding ->

        val contentModifier = Modifier.padding(10.dp)

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            HeaderText(
                modifier = Modifier.fillMaxWidth(),
                text = "Подготовка соревнования",
                bold = true,
            )

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как создать соревнование?",
            ) {
                Text(
                    modifier = contentModifier,
                    text = buildAnnotatedString {
                        append("На главном экране нужно нажать кнопку  ")
                        appendInlineContent(plusIcon.first)
                        append("  в правом нижнем углу экрана")
                    },
                    inlineContent = mapOf(plusIcon),
                )
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как изменить название / описание соревнования?",
            ) {
                Text(
                    modifier = contentModifier,
                    text = buildAnnotatedString {
                        append("Перейдите к соревнованию > ")
                        appendInlineContent(plusIcon.first)
                        append(" > `Описание` > Сделайте изменения > ")
                        appendInlineContent(checkIcon.first)
                    },
                    inlineContent = mapOf(plusIcon, checkIcon),
                )
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как добавить участников?",
            ) {
                Column(
                    modifier = contentModifier,
                ) {
                    Text("● Ручное добавление")
                    Text(
                        text = buildAnnotatedString {
                            append("Выберите соревнование > ")
                            appendInlineContent(personIcon.first)
                            append(" > ")
                            appendInlineContent(plusIcon.first)
                            append(" > ")
                            appendInlineContent(tableIcon.first)
                        },
                        inlineContent = mapOf(personIcon, plusIcon, tableIcon),
                    )
                    Text("● Добавление при помощи Excel таблиц")
                    Text(
                        text = buildAnnotatedString {
                            append("Выберите соревнование > ")
                            appendInlineContent(personIcon.first)
                            append(" > ")
                            appendInlineContent(plusIcon.first)
                            append(" > ")
                            appendInlineContent(pencilIcon.first)
                        },
                        inlineContent = mapOf(personIcon, plusIcon, pencilIcon),
                    )
                }
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как редактировать список дисциплин?",
            ) {
                Column(
                    modifier = contentModifier
                ) {
                    Text("● Добавление")
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = buildAnnotatedString {
                            append("Выберите соревнование > ")
                            appendInlineContent(plusIcon.first)
                            append(" > Дисциплина")
                        },
                        inlineContent = mapOf(plusIcon),
                    )
                    Text("● Удаление")
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Выберите соревнование > Удерживайте название дисциплины > `Удалить`"
                    )
                }
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как должна выглядеть импортируемая таблица с участниками?",
            ) {
                Column(
                    modifier = contentModifier
                ) {
                    Text("Столбцы должны находиться рядом друг с другом в следующем порядке:")
                    Spacer(Modifier.height(MaterialTheme.spacing.small))
                    Image(
                        modifier=Modifier.fillMaxWidth(),
                        painter = painterResource(R.drawable.table_format),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }

            HeaderText(
                modifier = Modifier.fillMaxWidth(),
                text = "Проведение соревнования",
                bold = true,
            )

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как добавить результат участника?",
            ) {
                Column(
                    modifier = contentModifier,
                ) {
                    Text("Перейдите к ячейке результата")
                    Text(" ● Если нужно изменить номер, нажмите на левую часть")
                    Text(" ● Если нужно изменить результат, нажмите на правую часть")
                }
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как изменить результат участника?",
            ) {
                Column(
                    modifier = contentModifier,
                ) {
                    Text("Перейдите к ячейке результата > удерживайте >")
                    Text(" ● Если нужно изменить номер, нажмите на левую часть")
                    Text(" ● Если нужно изменить результат, нажмите на правую часть")
                }
            }

            HeaderText(
                modifier = Modifier.fillMaxWidth(),
                text = "Подведение итогов соревнования",
                bold = true,
            )

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как скачать таблицу с результатами?",
            ) {

                Text(
                    modifier = contentModifier,
                    text = buildAnnotatedString {
                        append("Перейдите к соревнованию > ")
                        appendInlineContent(tripleDotIcon.first)
                        append(" > Скачать")
                    },
                    inlineContent = mapOf(tripleDotIcon),
                )
            }

            SimpleExpandableCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Как посмотреть результаты?",
            ) {
                Text(
                    modifier = contentModifier,
                    text = buildAnnotatedString {
                        append("Перейдите к соревнованию > ")
                        appendInlineContent(resultsIcon.first)
                    },
                    inlineContent = mapOf(resultsIcon),
                )
            }
        }
    }
}

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String,
    bold: Boolean = false,
) {
    Text(
        text = text,
        fontSize = 25.sp,
        fontWeight = if (bold) FontWeight.Bold else null
    )
}