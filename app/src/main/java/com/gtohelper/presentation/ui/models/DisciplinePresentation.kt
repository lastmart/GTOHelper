package com.gtohelper.presentation.ui.models

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SubDiscipline

class DisciplinePresentation(
    imageResource: Int,
    name: String,
    subDisciplines: List<SubDiscipline>,
    var isExpanded: Boolean = false
): Discipline(imageResource, name, subDisciplines)