package com.gtohelper.data.database.relations

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionSubDisciplineDao {

    @Transaction
    @Query("SELECT * FROM competitions_table")
    fun getCompetitionWithSubDisciplines(): Flow<List<CompetitionWithSubDisciplines>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(competitionDiscipline: CompetitionSubDisciplineCrossRef)

    @Transaction
    @Delete
    suspend fun delete(competitionDiscipline: CompetitionSubDisciplineCrossRef)
}