package com.gtohelper.data.database.competitor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gtohelper.data.database.relations.CompetitorWithSportResults
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitorDao {

    @Query("SELECT * FROM competitors_table WHERE competitionId=:competitionIid")
    fun getCompetitionAllCompetitors(competitionIid: Int): List<CompetitorEntity> // TODO suspend

    @Query("SELECT * FROM competitors_table WHERE competitionId=:competitionIid")
    fun getCompetitionCompetitors(competitionIid: Int): Flow<List<CompetitorEntity>>

    @Query("SELECT COUNT(*) FROM competitors_table WHERE competitionId=:competitionId")
    fun getCompetitionCompetitorCount(competitionId: Int): Flow<Int>

    @Query("SELECT * FROM competitors_table WHERE competitionId=:competitionId AND number=:competitorNumber")
    suspend fun getCompetitorByNumberInCompetition(
        competitorNumber: Int,
        competitionId: Int
    ): CompetitorEntity?

    @Query("SELECT * FROM competitors_table WHERE id=:competitorId")
    suspend fun getCompetitorById(competitorId: Int): CompetitorEntity?

    @Transaction
    @Query("SELECT * FROM competitors_table WHERE competitionId = :competitionId")
    fun getCompetitorsWithSportResults(competitionId: Int): Flow<List<CompetitorWithSportResults>>

    @Insert
    suspend fun create(competitor: CompetitorEntity)

    @Update
    suspend fun update(competitor: CompetitorEntity)

    @Delete
    suspend fun delete(competitor: CompetitorEntity)
}