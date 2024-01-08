package com.gtohelper.data.database.sport_result

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sport_results",
    foreignKeys = [
        ForeignKey(
            entity = SportResultEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitorId"],
            onDelete = ForeignKey.CASCADE,
        ),
        // TODO: sportname -> discplineId - Needed for results autodeletion after deleting a discipline from competition
//        ForeignKey(
//            entity = CompetitionSubDisciplineCrossRef::class,
//            parentColumns = ["competitionId", "subDisciplineId"],
//            childColumns = ["competitionId", "sportName"],
//            onDelete = ForeignKey.CASCADE
//        )
    ]
)
data class SportResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sportName: String,
    val competitionId: Int,
    val value: Int,
    val competitorId: Int,
)