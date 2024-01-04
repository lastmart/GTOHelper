package com.gtohelper.presentation.ui.competition_detail

import com.gtohelper.domain.models.Competition

sealed class CompetitionDetailUiState {
    data object Loading : CompetitionDetailUiState()
    data class Loaded(
        val competition: Competition,
        val competitorsCount: Int,
    ) : CompetitionDetailUiState()

    data object LoadFailed : CompetitionDetailUiState()
}