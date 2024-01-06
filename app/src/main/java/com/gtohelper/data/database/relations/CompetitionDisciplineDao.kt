package com.gtohelper.data.database.relations

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionDisciplineDao {

    @Transaction
    @Query("SELECT * FROM competitions_table")
    fun getDisciplines(): Flow<List<CompetitionWithDisciplines>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(competitionDiscipline: CompetitionDisciplineCrossRef)

    @Transaction
    @Delete
    suspend fun delete(competitionDiscipline: CompetitionDisciplineCrossRef)
}