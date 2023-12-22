package com.gtohelper.data.mappers

import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.domain.models.Competition


fun Competition.toEntity(): CompetitionEntity {
    return CompetitionEntity(
        id = id,
        name = name,
        description = description,
    )
}

fun CompetitionEntity.toDomainModel(): Competition {
    return Competition(
        id = id,
        name = name,
        description = description,
    )
}