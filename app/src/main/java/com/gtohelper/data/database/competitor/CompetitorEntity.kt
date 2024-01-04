package com.gtohelper.data.database.competitor

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.domain.models.Gender

@Entity(
    tableName = "competitors_table",
    foreignKeys = [
        ForeignKey(
            entity = CompetitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["number"], unique = true),
        Index(value = ["competitionId"], unique = false),
    ]
)
data class CompetitorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val number: Int,
    val name: String,
    val gender: Gender,
    val teamName: String,
    val competitionId: Int,
    val degree: Int
)