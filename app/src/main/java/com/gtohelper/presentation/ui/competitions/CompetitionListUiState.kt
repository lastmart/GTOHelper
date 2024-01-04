package com.gtohelper.presentation.ui.competitions

import com.gtohelper.domain.models.Competition


sealed class CompetitionListUiState {
    data object Loading : CompetitionListUiState()
    data class Loaded(val competitions: List<Competition>) : CompetitionListUiState()
}
