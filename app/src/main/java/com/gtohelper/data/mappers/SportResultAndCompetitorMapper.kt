package com.gtohelper.data.mappers

import com.gtohelper.data.database.relational.SportResultAndCompetitorEntity
import com.gtohelper.domain.models.SportResultAndCompetitor


fun SportResultAndCompetitorEntity.toDomainModel(): SportResultAndCompetitor {
    return SportResultAndCompetitor(
        result = sportResult.toDomainModel(),
        competitor = competitor.toDomainModel(),
    )
}