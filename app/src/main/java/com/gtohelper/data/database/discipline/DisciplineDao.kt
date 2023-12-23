package com.gtohelper.data.database.discipline

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DisciplineDao {

    @Query("SELECT * FROM disciplines_table WHERE competitionId = :competitionId")
    suspend fun getDisciplines(competitionId: Int): List<DisciplineEntity>

    @Query(""" 
        SELECT * FROM disciplines_table 
        WHERE competitionId = :competitionId
        AND name = :name 
        """)
    suspend fun getDisciplineByName(name: String, competitionId: Int): DisciplineEntity?

    @Upsert
    suspend fun upsertDiscipline(discipline: DisciplineEntity)

    @Upsert
    suspend fun upsertDisciplines(disciplines: List<DisciplineEntity>)
}