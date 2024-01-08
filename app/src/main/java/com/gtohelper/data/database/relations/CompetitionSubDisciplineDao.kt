package com.gtohelper.data.database.relations

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.discipline.SubDisciplineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionSubDisciplineDao {
    @Query("SELECT * FROM sub_disciplines_table WHERE name=:name")
    suspend fun getBy(name: String) : SubDisciplineEntity?

    @Query("SELECT * FROM sub_disciplines_table WHERE id=:id")
    suspend fun getBy(id: Int) : SubDisciplineEntity?

    @Transaction
    @Query("SELECT * FROM competitions_table")
    fun getCompetitionWithSubDisciplines(): Flow<List<CompetitionWithSubDisciplines>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(competitionDiscipline: CompetitionSubDisciplineCrossRef)

    @Transaction
    @Delete
    suspend fun delete(competitionDiscipline: CompetitionSubDisciplineCrossRef)
}