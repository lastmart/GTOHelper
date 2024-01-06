package com.gtohelper.data.database.relations

import androidx.room.Entity

@Entity(primaryKeys = ["competitionId", "disciplineId"])
data class CompetitionDisciplineCrossRef(
    val competitionId: Int,
    val disciplineId: Int
)