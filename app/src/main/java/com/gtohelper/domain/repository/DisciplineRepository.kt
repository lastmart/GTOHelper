package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline

interface DisciplineRepository {

    suspend fun getDisciplines(): List<Discipline>

    suspend fun getSelectedDisciplines(): List<Discipline>

    suspend fun getNotSelectedDisciplines(): List<Discipline>

    suspend fun addDisciplineToSelected(discipline: Discipline)

    suspend fun deleteDisciplineFromSelectedByName(name: String)
}