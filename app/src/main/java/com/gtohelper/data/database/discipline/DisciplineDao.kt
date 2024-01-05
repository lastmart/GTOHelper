package com.gtohelper.data.database.discipline

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {

    @Query("SELECT * FROM disciplines_table WHERE competitionId = :competitionId")
    fun getDisciplines(competitionId: Int): Flow<List<DisciplineEntity>>

    @Query(""" 
        SELECT * FROM disciplines_table 
        WHERE competitionId = :competitionId
        AND name = :name 
        """)
    suspend fun getDisciplineByName(name: String, competitionId: Int): DisciplineEntity?

    @Delete
    suspend fun deleteDiscipline(discipline: DisciplineEntity)

    @Upsert
    suspend fun upsertDiscipline(discipline: DisciplineEntity)

    @Upsert
    suspend fun upsertDisciplines(disciplines: List<DisciplineEntity>)
}