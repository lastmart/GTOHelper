package com.gtohelper.data.repository

import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository

class CompetitorRepositoryImpl : CompetitorRepository {

    override suspend fun getCompetitors(): List<Competitor> {
        TODO("Not yet implemented")
    }
}