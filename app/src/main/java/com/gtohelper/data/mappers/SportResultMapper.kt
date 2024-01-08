package com.gtohelper.data.mappers

import com.gtohelper.data.database.sport_result.SportResultEntity
import com.gtohelper.domain.models.SportResult


fun SportResult.toEntity(): SportResultEntity {
    return SportResultEntity(
        id = id,
        disciplineId = disciplineId,
        value = value,
        competitorId = competitorId,
        competitionId = competitionId,
    )
}

fun SportResultEntity.toDomainModel(): SportResult {
    return SportResult(
        id = id,
        disciplineId = disciplineId,
        competitorId = competitorId,
        value = value,
        competitionId = competitionId,
    )
}