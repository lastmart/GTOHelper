package com.gtohelper.data.repository

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository

class CompetitorRepositoryImpl : CompetitorRepository {

    override suspend fun getCompetitors(): List<Competitor> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertCompetitor(competitor: Competitor) {
        TODO("Not yet implemented")
    }

    override suspend fun changeCompetitor(oldCompetitor: Competitor, newCompetitor: Competitor) {
        TODO("Not yet implemented")
    }
}