package com.gtohelper.data.database.competitor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CompetitorDao {

    @Query(
        "SELECT * FROM competitors_table"
    )
    suspend fun getCompetitors(): List<CompetitorEntity>

    @Upsert
    suspend fun upsertCompetitor(competitor: CompetitorEntity)

    @Delete
    suspend fun deleteCompetitor(competitor: CompetitorEntity)
}