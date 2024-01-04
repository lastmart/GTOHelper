package com.gtohelper.presentation.ui.models

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.DisciplinePointType

class DisciplinePresentation(
    imageResource: Int,
    name: String,
    subDisciplines: List<DisciplinePresentation>,
    type: DisciplinePointType,
    var isExpanded: Boolean = false,
) : Discipline(
    name = name,
    imageResource = imageResource,
    subDisciplines = subDisciplines,
    type = type
)