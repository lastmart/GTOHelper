package com.gtohelper.data.database.competitor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gtohelper.domain.models.Gender

@Entity(tableName = "competitors_table")
data class CompetitorEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nameCompetitor: String,
    val age: Int,
    val gender: Gender,
    val nameTeam: String,
    val degree: Int
)