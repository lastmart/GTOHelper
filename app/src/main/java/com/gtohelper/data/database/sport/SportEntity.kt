package com.gtohelper.data.database.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports_table")
data class SportEntity(
    @PrimaryKey
    val name: String,
    val competitorIds: List<Int>
)