package com.gtohelper.domain.repository

import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SportResultAndCompetitor
import kotlinx.coroutines.flow.Flow

interface SportResultRepository {
    suspend fun getBy(id: Int): SportResult?
    suspend fun getBy(competitionId: Int, disciplineId: Int, competitorId: Int): SportResult?
    suspend fun create(sportResult: SportResult)
    suspend fun update(sportResult: SportResult)
    suspend fun delete(sportResult: SportResult)
    fun getResultsAndCompetitors(
        competitionId: Int,
        disciplineId: Int
    ): Flow<List<SportResultAndCompetitor>>

    fun getCompetitionDisciplineResults(
        competitionId: Int,
        disciplineId: Int
    ): Flow<List<SportResult>>
}