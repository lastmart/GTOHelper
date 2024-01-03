package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {

    suspend fun getDisciplines(): List<Discipline>

    fun getSelectedDisciplines(): Flow<List<Discipline>>

    suspend fun getNotSelectedDisciplines(): List<Discipline>

    suspend fun addDisciplineToSelected(discipline: Discipline)

    suspend fun deleteDisciplineFromSelectedByName(name: String)
}