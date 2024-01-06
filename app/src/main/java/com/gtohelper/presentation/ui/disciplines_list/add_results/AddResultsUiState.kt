package com.gtohelper.presentation.ui.disciplines_list.add_results

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SportResult

data class AddResultsUiState(
    val discipline: Discipline? = null,
    val currentNumber: Int = 0,
    val currentResult: Int = 0,
)