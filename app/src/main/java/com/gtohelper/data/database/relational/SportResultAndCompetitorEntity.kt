package com.gtohelper.data.database.relational

import androidx.room.Embedded
import androidx.room.Relation
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.sport_result.SportResultEntity


data class SportResultAndCompetitorEntity(
    @Embedded val sportResult: SportResultEntity,
    @Relation(
        parentColumn = "competitorId",
        entityColumn = "id"
    )
    val competitor: CompetitorEntity
)