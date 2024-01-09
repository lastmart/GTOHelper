package com.gtohelper.data.database.discipline

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.gtohelper.data.database.relations.DisciplineWithSubDisciplines
import com.gtohelper.data.database.relations.SubDisciplineWithCompetitorsWithResults
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplineDao {

    @Query("SELECT * FROM disciplines_table")
    fun getDisciplines(): Flow<List<DisciplineEntity>>

    @Transaction
    @Query("SELECT * FROM disciplines_table")
    fun getDisciplinesWithSubDisciplines(): Flow<List<DisciplineWithSubDisciplines>>

    @Query("SELECT * FROM disciplines_table WHERE name = :name")
    suspend fun getDisciplineByName(name: String): DisciplineEntity?

    @Query("SELECT * FROM sub_disciplines_table")
    fun getSubDisciplines(): Flow<List<SubDisciplineEntity>>

    @Query("SELECT * FROM sub_disciplines_table WHERE id = :id ")
    suspend fun getSubDisciplineById(id: Int): SubDisciplineEntity?

    @Query("SELECT * FROM sub_disciplines_table WHERE name = :name")
    suspend fun getSubDisciplineByName(name: String): SubDisciplineEntity?

    @Transaction
    @Query("SELECT * FROM sub_disciplines_table")
    suspend fun getDisciplineWithCompetitorsWithResults(): List<SubDisciplineWithCompetitorsWithResults>
}