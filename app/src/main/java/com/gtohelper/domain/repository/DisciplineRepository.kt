package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SubDiscipline
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {

    fun getDisciplines(): Flow<List<Discipline>>

    fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>>

    fun getNotSelectedDisciplines(competitionId: Int): Flow<List<Discipline>>

    suspend fun addSubDisciplineToSelectedByName(name: String, competitionId: Int)

    suspend fun deleteSubDisciplineFromSelectedByName(name: String, competitionId: Int)
}