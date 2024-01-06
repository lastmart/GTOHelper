package com.gtohelper.presentation.ui.mappers

import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation

fun SubDiscipline.toDisciplinePresentation(): DisciplinePresentation {
    return DisciplinePresentation(
        name = name,
        imageResource = imageResource,
        subDisciplines = emptyList(),
        type = type
    )
}