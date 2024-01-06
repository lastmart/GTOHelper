package com.gtohelper.presentation.ui.models

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.SubDiscipline

class DisciplinePresentation(
    imageResource: Int,
    name: String,
    subDisciplines: List<SubDiscipline>,
    type: DisciplinePointType,
    var isExpanded: Boolean = false,
) : Discipline(
    name = name,
    imageResource = imageResource,
    subDisciplines = subDisciplines,
    type = type
)