/*
package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.data.database.discipline.DisciplineEntity

@Entity
data class DisciplineWithCompetitions(
    @Embedded
    val discipline: DisciplineEntity,

    @Relation(
        parentColumn = "disciplineId",
        entityColumn = "competitionId",
        associateBy = Junction(CompetitionDisciplineCrossRef::class)
    )
    val competitions: List<CompetitionEntity>
)*/
