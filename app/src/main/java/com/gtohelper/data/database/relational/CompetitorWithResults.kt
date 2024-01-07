package com.gtohelper.data.database.relational

import androidx.room.Embedded
import androidx.room.Relation
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.sport_result.SportResultEntity

data class CompetitorWithResults(
    @Embedded val competitor: CompetitorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "competitorId"
    )
    val results: List<SportResultEntity>
)