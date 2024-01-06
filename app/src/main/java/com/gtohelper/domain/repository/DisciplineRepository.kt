package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {

    fun getDisciplines(): Flow<List<Discipline>>

    fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>>

    fun getNotSelectedDisciplines(competitionId: Int): Flow<List<Discipline>>

    suspend fun addDisciplineToSelected(discipline: Discipline, competitionId: Int)

    suspend fun deleteDisciplineFromSelectedByName(name: String, competitionId: Int)
}