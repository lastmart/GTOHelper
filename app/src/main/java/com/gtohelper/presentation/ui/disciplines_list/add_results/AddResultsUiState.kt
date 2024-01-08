package com.gtohelper.presentation.ui.disciplines_list.add_results

import com.gtohelper.domain.models.Discipline


sealed class AddResultsUiState {

    data object Loading : AddResultsUiState()
    data class Loaded(
        val discipline: Discipline,
        val number: Int = 0,
        val result: Int = 0,
    ) : AddResultsUiState()
}