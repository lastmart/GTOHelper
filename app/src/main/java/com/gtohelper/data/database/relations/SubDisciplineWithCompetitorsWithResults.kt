package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.gtohelper.data.database.discipline.SubDisciplineEntity
import com.gtohelper.data.database.relational.SportResultAndCompetitorEntity
import com.gtohelper.data.database.sport_result.SportResultEntity


data class SubDisciplineWithCompetitorsWithResults(
    @Embedded
    val disciplineEntity: SubDisciplineEntity,

    @Relation(
        entity = SportResultEntity::class,
        parentColumn = "id",
        entityColumn = "disciplineId"
    )
    val competitorsWithResults: List<SportResultAndCompetitorEntity>
)