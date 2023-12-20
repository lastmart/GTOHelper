package com.gtohelper.data.database.sport

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "sports_table")
data class SportResultEntity(
    @PrimaryKey
    val name: String,
    val competitorId: Int,
    val resultTime: LocalTime?,
    val resultAmount: Double?
)