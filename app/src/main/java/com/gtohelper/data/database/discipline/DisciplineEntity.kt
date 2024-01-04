package com.gtohelper.data.database.discipline

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gtohelper.domain.models.DisciplinePointType


// TODO: Implement me
@Entity(tableName = "disciplines")
data class DisciplineEntity(
    @PrimaryKey
    val name: String,
    val pointType: DisciplinePointType,
)