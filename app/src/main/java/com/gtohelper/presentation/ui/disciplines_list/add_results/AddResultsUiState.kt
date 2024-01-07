package com.gtohelper.presentation.ui.disciplines_list.add_results

import com.gtohelper.domain.models.Discipline

data class AddResultsUiState(
    val discipline: Discipline? = null,
    val number: Int = 0,
    val result: Int = 0,
)