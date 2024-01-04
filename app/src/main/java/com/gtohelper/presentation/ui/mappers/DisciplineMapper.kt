package com.gtohelper.presentation.ui.mappers

import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.models.DisciplinePresentation

fun Discipline.toDisciplinePresentation(): DisciplinePresentation {
    return DisciplinePresentation(
        imageResource = imageResource,
        name = name,
        subDisciplines = subDisciplines.map {
            DisciplinePresentation(
                imageResource = it.imageResource,
                name = it.name,
                subDisciplines = listOf(),
                type = it.type
            )
        },
        isExpanded = false,
        type = type
    )
}

fun DisciplinePresentation.toDiscipline(isSelected: Boolean = false): Discipline {
    return Discipline(
        imageResource = imageResource,
        name = name,
        subDisciplines = subDisciplines,
        isSelected = isSelected,
        type = type
    )
}
