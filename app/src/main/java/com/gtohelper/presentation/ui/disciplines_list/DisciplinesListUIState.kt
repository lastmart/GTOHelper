package com.gtohelper.presentation.ui.disciplines_list

import com.gtohelper.domain.models.SubDiscipline

data class DisciplinesListUIState(
    val subDisciplines: List<SubDiscipline> = listOf()
)