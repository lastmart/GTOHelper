package com.gtohelper.data.mappers

import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.domain.models.Competitor

fun Competitor.toEntity() : CompetitorEntity {
    return CompetitorEntity(
        id=id,
        name=name,
        competitionId=competitionId,
        gender=gender,
        teamName=teamName,
        number=number,
        degree=degree,
    )
}

fun CompetitorEntity.toDomainModel() : Competitor {
    return Competitor(
        id=id,
        name=name,
        competitionId=competitionId,
        gender=gender,
        teamName=teamName,
        number=number,
        degree=degree,
    )
}