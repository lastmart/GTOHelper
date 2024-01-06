package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.data.database.discipline.DisciplineEntity

// @Entity
// Foreign Key
// Transaction in dao
data class CompetitionWithDisciplines(
    @Embedded val competition: CompetitionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = DisciplineEntity::class,
        associateBy = Junction(
            value = CompetitionDisciplineCrossRef::class,
            parentColumn = "competitionId",
            entityColumn = "disciplineId"
        )
    )
    val disciplines: List<DisciplineEntity>
)