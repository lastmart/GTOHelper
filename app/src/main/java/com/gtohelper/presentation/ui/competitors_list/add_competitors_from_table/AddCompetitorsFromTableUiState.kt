package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

sealed class AddCompetitorsFromTableUiState {
    data object Initial : AddCompetitorsFromTableUiState()
    data object Loading : AddCompetitorsFromTableUiState()
    data object Success : AddCompetitorsFromTableUiState()
    data class Failed(val reason: String) : AddCompetitorsFromTableUiState()
}