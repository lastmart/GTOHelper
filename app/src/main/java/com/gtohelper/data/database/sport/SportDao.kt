package com.gtohelper.data.database.sport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SportDao {

    @Query(
        "SELECT * FROM sports_table"
    )
    suspend fun getSports(): List<SportEntity>


    @Query(
        """
            SELECT * FROM sports_table
            WHERE name = :sportName
            """
    )
    suspend fun getSportCompetitors(sportName: String): List<SportEntity>

    @Upsert
    suspend fun upsertSport(sport: SportEntity)

    @Delete
    suspend fun deleteSport(sport: SportEntity)
}