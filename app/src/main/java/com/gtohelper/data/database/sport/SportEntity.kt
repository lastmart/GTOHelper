package com.gtohelper.data.database.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports_table")
data class SportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val competitorIds: List<Int>?
)