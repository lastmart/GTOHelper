package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.sport_result.SportResultEntity

data class CompetitorWithSportResults(

    @Embedded
    val competitor: CompetitorEntity,

    @Relation(
        entity = SportResultEntity::class,
        parentColumn = "id",
        entityColumn = "competitorId"
    )
    val sportResults: List<SportResultEntity>
)