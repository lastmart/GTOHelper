package com.gtohelper.data.database.competition

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "competitions_table")
data class CompetitionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
)