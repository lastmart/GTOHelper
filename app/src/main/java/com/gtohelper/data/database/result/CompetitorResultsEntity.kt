package com.gtohelper.data.database.result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetitorResultsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val competitorId: Int,
    val results: String // ??????
)