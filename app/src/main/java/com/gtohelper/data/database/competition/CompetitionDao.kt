package com.gtohelper.data.database.competition

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CompetitionDao {

    @Query("SELECT * FROM competitions_table")
    fun getAll(): Flow<List<CompetitionEntity>>

    @Query("SELECT * FROM competitions_table WHERE id=:id")
    suspend fun getById(id: Int) : CompetitionEntity?

    @Insert
    suspend fun create(competition: CompetitionEntity)

    @Update
    suspend fun update(competition: CompetitionEntity)

    @Delete
    suspend fun delete(competition: CompetitionEntity)
}