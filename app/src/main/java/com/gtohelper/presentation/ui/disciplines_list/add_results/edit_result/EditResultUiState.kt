package com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SportResult

sealed class EditResultUiState {
    data object Loading : EditResultUiState()
    data class Loaded(
        val pointType: DisciplinePointType,
        val result: SportResult,
        val competitor: Competitor,
    ) : EditResultUiState()

    data object Deleted: EditResultUiState()
}