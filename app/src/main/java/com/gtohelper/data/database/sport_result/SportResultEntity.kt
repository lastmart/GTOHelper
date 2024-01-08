package com.gtohelper.data.database.sport_result

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.relations.CompetitionSubDisciplineCrossRef

@Entity(
    tableName = "sport_results",
    foreignKeys = [
        ForeignKey(
            entity = CompetitorEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitorId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CompetitionSubDisciplineCrossRef::class,
            parentColumns = ["competitionId", "subDisciplineId"],
            childColumns = ["competitionId", "disciplineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SportResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val competitionId: Int,
    val disciplineId: Int,
    val competitorId: Int,
    val value: Int,
)