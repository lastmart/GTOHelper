package com.gtohelper.data.database.competitor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitorDao {

    @Query("SELECT * FROM competitors_table WHERE competitionId=:competitionIid")
    fun getCompetitionCompetitors(competitionIid: Int): Flow<List<CompetitorEntity>>

    @Query("SELECT * FROM competitors_table WHERE id =:id")
    suspend fun getCompetitorById(id: Int): CompetitorEntity

    @Insert
    suspend fun create(competitor: CompetitorEntity)

    @Update
    suspend fun update(competitor: CompetitorEntity)

    @Delete
    suspend fun delete(competitor: CompetitorEntity)
}