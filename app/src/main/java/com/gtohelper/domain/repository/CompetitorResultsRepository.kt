package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.SportResult
import kotlinx.coroutines.flow.Flow

interface CompetitorResultsRepository {
    fun getCompetitorsWithSportResults(competitionId: Int): Flow<List<Pair<Competitor, Int>>>

    suspend fun getTotalPoints(
        competitor: Competitor,
        sportResult: SportResult,
    ): Int
}