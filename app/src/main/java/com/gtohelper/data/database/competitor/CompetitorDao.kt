package com.gtohelper.data.database.competitor

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface CompetitorDao {

    @Upsert
    suspend fun upsertCompetitor(competitor: CompetitorEntity)
}