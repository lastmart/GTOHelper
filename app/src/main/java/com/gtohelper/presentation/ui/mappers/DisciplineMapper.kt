package com.gtohelper.presentation.ui.mappers

import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation

fun Discipline.toDisciplinePresentation(): DisciplinePresentation {
    return DisciplinePresentation(
        imageResource = imageResource,
        name = name,
        subDisciplines = subDisciplines.map {
            DisciplinePresentation(it.imageResource, it.name, listOf())
        },
        isExpanded = false
    )
}

fun DisciplinePresentation.toDiscipline(isSelected: Boolean = false): Discipline{
    return Discipline(
        imageResource = imageResource,
        name = name,
        subDisciplines = subDisciplines,
        isSelected = isSelected
    )
}
