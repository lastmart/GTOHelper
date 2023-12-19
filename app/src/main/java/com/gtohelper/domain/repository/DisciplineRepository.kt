package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Discipline

interface DisciplineRepository {

    suspend fun getDisciplines(): List<Discipline>
}