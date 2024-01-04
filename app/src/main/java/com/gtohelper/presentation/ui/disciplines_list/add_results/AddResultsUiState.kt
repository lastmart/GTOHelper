package com.gtohelper.presentation.ui.disciplines_list.add_results

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SportResult

sealed class AddResultsUiState {

    data object Loading : AddResultsUiState()
    data class Loaded(
        val discipline: Discipline,
        val results: List<SportResult>,
        val currentNumber: Int = 0,
        val currentResult: Long = 0,
    ) : AddResultsUiState()
}



