package com.gtohelper.data.mappers

import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.discipline.SubDisciplineEntity
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SubDiscipline

fun DisciplineEntity.toDomainDiscipline(): Discipline {
    return Discipline(
        name = name,
        imageResource = imageResource,
        subDisciplines = subDisciplines.map { it.toDomainSubDiscipline() },
        type = type
    )
}

fun SubDisciplineEntity.toDomainSubDiscipline(): SubDiscipline {
    return SubDiscipline(
        id = id,
        name = name,
        imageResource = imageResource,
        type = type,
    )
}

fun Discipline.toSubDiscipline(): SubDiscipline {
    return SubDiscipline(
        name = name,
        imageResource = imageResource,
        type = type
    )
}