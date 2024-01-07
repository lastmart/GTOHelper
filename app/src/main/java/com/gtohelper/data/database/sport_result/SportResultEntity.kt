package com.gtohelper.data.database.sport_result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sport_results")
data class SportResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sportName: String,
    val competitionId: Int,
    val value: Int,
    val competitorId: Int,
)