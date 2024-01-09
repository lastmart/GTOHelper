package com.gtohelper.data.database.sport_result

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gtohelper.data.database.relational.SportResultAndCompetitorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportResultDao {

    @Query(
        """
            SELECT * FROM sport_results
            WHERE competitionId = :competitionId AND disciplineId=:disciplineId
            """
    )
    fun getCompetitionSportResults(
        competitionId: Int,
        disciplineId: Int,
    ): Flow<List<SportResultEntity>>

    @Insert
    suspend fun create(sport: SportResultEntity)

    @Update
    suspend fun update(sport: SportResultEntity)

    @Delete
    suspend fun delete(sport: SportResultEntity)


    @Query("SELECT * FROM sport_results WHERE id=:id")
    suspend fun getBy(id: Int): SportResultEntity?


    @Query(
        """SELECT * FROM sport_results WHERE 
        competitionId=:competitionId AND 
        disciplineId=:disciplineId AND 
        competitorId=:competitorId"""
    )
    suspend fun getBy(
        competitionId: Int,
        disciplineId: Int,
        competitorId: Int
    ): SportResultEntity?

    @Transaction
    @Query("SELECT * FROM sport_results WHERE competitionId=:competitionId AND disciplineId=:disciplineId")
    fun getResultsAndCompetitors(
        competitionId: Int,
        disciplineId: Int
    ): Flow<List<SportResultAndCompetitorEntity>>
}