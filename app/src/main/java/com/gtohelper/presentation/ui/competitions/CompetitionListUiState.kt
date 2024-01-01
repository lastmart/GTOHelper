package com.gtohelper.presentation.ui.competitions

import com.gtohelper.domain.models.Competition

data class CompetitionListUiState(
    val competitions: List<Competition> = listOf(),
)
