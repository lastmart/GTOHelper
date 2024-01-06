package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.data.database.discipline.SubDisciplineEntity

// @Entity
// Foreign Key
// Transaction in dao
data class CompetitionWithSubDisciplines(
    @Embedded val competition: CompetitionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = SubDisciplineEntity::class,
        associateBy = Junction(
            value = CompetitionSubDisciplineCrossRef::class,
            parentColumn = "competitionId",
            entityColumn = "subDisciplineId"
        )
    )
    val subDisciplines: List<SubDisciplineEntity>
)