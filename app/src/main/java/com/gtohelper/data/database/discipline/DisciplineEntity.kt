package com.gtohelper.data.database.discipline

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gtohelper.data.database.Converters
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.domain.models.DisciplinePointType

@Entity(
    tableName = "disciplines_table",
    foreignKeys = [
        ForeignKey(
            entity = CompetitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
@TypeConverters(Converters::class)
data class DisciplineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val competitionId: Int,
    val name: String,
    val parentName: String? = null,
    val imageResource: Int,
    val isSelected: Boolean = false,
    val type: DisciplinePointType
)