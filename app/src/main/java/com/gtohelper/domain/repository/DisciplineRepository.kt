package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {

    suspend fun getDisciplines(competitionId: Int): List<Discipline>

    fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>>

    suspend fun getNotSelectedDisciplines(competitionId: Int): List<Discipline>

    suspend fun addDisciplineToSelected(discipline: Discipline, competitionId: Int)

    suspend fun deleteDisciplineFromSelectedByName(name: String, competitionId: Int)
}