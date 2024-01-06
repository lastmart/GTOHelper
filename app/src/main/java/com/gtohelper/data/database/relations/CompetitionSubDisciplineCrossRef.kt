package com.gtohelper.data.database.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import com.gtohelper.data.database.competition.CompetitionEntity

@Entity(primaryKeys = ["competitionId", "subDisciplineId"],
    foreignKeys = [
        ForeignKey(
            entity = CompetitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = CASCADE
        )
    ]
)
data class CompetitionSubDisciplineCrossRef(
    val competitionId: Int,
    val subDisciplineId: Int
)