package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Competitor

interface CompetitorRepository {

    suspend fun getCompetitors(): List<Competitor>
}