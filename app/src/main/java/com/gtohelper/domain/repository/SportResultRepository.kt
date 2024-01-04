package com.gtohelper.domain.repository

import com.gtohelper.domain.models.SportResult
import kotlinx.coroutines.flow.Flow

interface SportResultRepository {

    suspend fun create(sportResult: SportResult)
    suspend fun update(sportResult: SportResult)
    suspend fun delete(sportResult: SportResult)

    fun getCompetitionDisciplineResults(
        competitionId: Int,
        disciplineId: String
    ): Flow<List<SportResult>>
}