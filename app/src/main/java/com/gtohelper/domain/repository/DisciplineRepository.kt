package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline

interface DisciplineRepository {

    suspend fun getDisciplines(competitionId: Int): List<Discipline>

    suspend fun getSelectedDisciplines(competitionId: Int): List<Discipline>

    suspend fun getNotSelectedDisciplines(competitionId: Int): List<Discipline>

    suspend fun addDisciplineToSelected(discipline: Discipline, competitionId: Int)

    suspend fun deleteDisciplineFromSelectedByName(name: String, competitionId: Int)
}