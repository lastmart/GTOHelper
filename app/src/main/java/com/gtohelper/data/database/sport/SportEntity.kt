package com.gtohelper.data.database.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    // TODO
)