package com.gtohelper.data.database.discipline

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gtohelper.data.database.Converters
import com.gtohelper.domain.models.DisciplinePointType

@Entity(tableName = "disciplines_table", primaryKeys = ["competitionId", "name"])
@TypeConverters(Converters::class)
data class DisciplineEntity(
    val competitionId: Int,
    //@PrimaryKey(autoGenerate = false)
    val name: String,
    val parentName: String? = null,
    val imageResource: Int,
    val isSelected: Boolean = false,
    val type: DisciplinePointType
)