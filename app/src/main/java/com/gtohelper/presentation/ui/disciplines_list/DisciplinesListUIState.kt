package com.gtohelper.presentation.ui.disciplines_list

import com.gtohelper.presentation.ui.models.DisciplinePresentation

data class DisciplinesListUIState(
    val disciplines: List<DisciplinePresentation> = listOf()
)