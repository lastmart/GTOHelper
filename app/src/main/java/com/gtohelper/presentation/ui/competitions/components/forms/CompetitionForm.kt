package com.gtohelper.presentation.ui.competitions.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gtohelper.presentation.components.composables.input_fields.AppTextField

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
        )

        AppTextField(
            modifier = Modifier.fillMaxSize(),
            value = uiState.description,
            label = "Описание",
            onValueChange = { onEvent(CompetitionFormEvent.UpdateDescription(it)) },
            maxLength = 200,
        )
    }
}