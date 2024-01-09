package com.gtohelper.presentation.ui.competitions.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gtohelper.presentation.components.composables.input_fields.AppTextField
import com.gtohelper.presentation.components.composables.input_fields.AppTextFieldDefaults

@Preview
@Composable
fun PreviewCompetitionForm() {
    CompetitionForm(
        uiState = CompetitionFormState(
            name = "new",
            description = "asd"
        )
    )
}

@Composable
fun CompetitionForm(
    modifier: Modifier = Modifier,
    uiState: CompetitionFormState,
    onEvent: (CompetitionFormEvent) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.name,
            label = "Название",
            onValueChange = { onEvent(CompetitionFormEvent.UpdateName(it)) },
            maxLength = 50,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            )
        )

        AppTextField(
            modifier = Modifier.fillMaxSize(),
            value = uiState.description,
            label = "Описание",
            onValueChange = { onEvent(CompetitionFormEvent.UpdateDescription(it)) },
            maxLength = 200,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            )
        )
    }
}