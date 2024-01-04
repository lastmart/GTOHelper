package com.gtohelper.data.database.sport_result

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SportResultDao {

    @Query(
        """
            SELECT * FROM sport_results
            WHERE competitionId = :competitionId AND sportName=:sportName
            """
    )
    fun getCompetitionSportResults(
        competitionId: Int,
        sportName: String,
    ): Flow<List<SportResultEntity>>
    
    @Insert
    suspend fun create(sport: SportResultEntity)

    @Update
    suspend fun update(sport: SportResultEntity)

    @Delete
    suspend fun delete(sport: SportResultEntity)

    @Delete
    suspend fun deleteResult(sport: SportResultEntity)
}