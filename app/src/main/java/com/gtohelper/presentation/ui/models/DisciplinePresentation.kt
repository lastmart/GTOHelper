package com.gtohelper.presentation.ui.models

import com.gtohelper.domain.models.Discipline

class DisciplinePresentation(
    imageResource: Int,
    name: String,
    subDisciplines: List<DisciplinePresentation>,
    var isExpanded: Boolean = false
) : Discipline(name, imageResource, subDisciplines)