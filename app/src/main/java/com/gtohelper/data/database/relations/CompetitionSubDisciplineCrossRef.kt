package com.gtohelper.data.database.relations

import androidx.room.Entity

@Entity(primaryKeys = ["competitionId", "subDisciplineId"])
data class CompetitionSubDisciplineCrossRef(
    val competitionId: Int,
    val subDisciplineId: Int
)