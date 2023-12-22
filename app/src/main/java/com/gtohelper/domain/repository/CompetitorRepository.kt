package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Competitor
import kotlinx.coroutines.flow.Flow

interface CompetitorRepository {
    fun getCompetitionCompetitors(competitionId: Int): Flow<List<Competitor>>
    suspend fun create(competitor: Competitor)
    suspend fun update(competitor: Competitor)
    suspend fun delete(competitor: Competitor)
}