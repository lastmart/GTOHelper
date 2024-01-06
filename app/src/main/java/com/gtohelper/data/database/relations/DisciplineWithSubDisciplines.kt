package com.gtohelper.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.discipline.SubDisciplineEntity


data class DisciplineWithSubDisciplines(
    @Embedded val discipline: DisciplineEntity,

    @Relation(
        entity = SubDisciplineEntity::class,
        parentColumn = "name",
        entityColumn = "parentName"
    )
    val subDisciplines: List<SubDisciplineEntity>
)