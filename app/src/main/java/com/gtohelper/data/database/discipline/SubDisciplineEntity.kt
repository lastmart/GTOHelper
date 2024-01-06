package com.gtohelper.data.database.discipline

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gtohelper.domain.models.DisciplinePointType

@Entity(tableName = "sub_disciplines_table")
data class SubDisciplineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val parentName: String = "",
    val name: String,
    val imageResource: Int,
    val type: DisciplinePointType
)