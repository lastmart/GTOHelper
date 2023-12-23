package com.gtohelper.data.database.discipline

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disciplines_table")
data class DisciplineEntity(
    val competitionId: Int,
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val parentName: String? = null,
    val imageResource: Int,
    val isSelected: Boolean = false
)