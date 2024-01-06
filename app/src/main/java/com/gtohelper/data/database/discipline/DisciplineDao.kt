package com.gtohelper.data.database.discipline

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {

    //  @Query("SELECT * FROM disciplines_table WHERE competitionId = :competitionId")

    @Query("SELECT * FROM disciplines_table")
    suspend fun getAllDisciplines(): List<DisciplineEntity>

    @Query("SELECT * FROM disciplines_table")
    fun getDisciplines(): Flow<List<DisciplineEntity>>

    /*@Query("""
        SELECT * FROM disciplines_table 
        WHERE competitionId = :competitionId
        AND name = :name 
        """)*/
    @Query(
        """ 
        SELECT * FROM disciplines_table 
        WHERE name = :name 
        """
    )
    suspend fun getDisciplineByName(name: String): DisciplineEntity?

    @Delete
    suspend fun deleteDiscipline(discipline: DisciplineEntity)

    @Upsert
    suspend fun upsertDiscipline(discipline: DisciplineEntity)

    @Upsert
    suspend fun upsertDisciplines(disciplines: List<DisciplineEntity>)
}