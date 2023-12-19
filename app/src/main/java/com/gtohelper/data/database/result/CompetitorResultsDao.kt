package com.gtohelper.data.database.result

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CompetitorResultsDao {

    @Query(
        "SELECT * FROM competitors_results_table"
    )
    suspend fun getResults(): List<CompetitorResultsEntity>

    @Upsert
    suspend fun upsertResult(result: CompetitorResultsEntity)

    @Delete
    suspend fun deleteResult(result: CompetitorResultsEntity)
}