package com.gtohelper.data.database.competitor

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gtohelper.domain.models.Gender

@Entity(
    tableName = "competitors_table",
    foreignKeys = [
        ForeignKey(
            entity = CompetitorEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class CompetitorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val gender: Gender,
    val teamName: String,
    val competitionId: Int,
    val number: Int,
    val degree: Int
)