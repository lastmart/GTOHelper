package com.gtohelper.data.mappers

import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.domain.models.Discipline

fun DisciplineEntity.toSubDiscipline(): Discipline{
    return Discipline(
        name, imageResource, listOf(), isSelected, type
    )
}