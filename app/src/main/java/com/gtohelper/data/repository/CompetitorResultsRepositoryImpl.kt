package com.gtohelper.data.repository

import com.gtohelper.domain.repository.CompetitorResultsRepository

class CompetitorResultsRepositoryImpl : CompetitorResultsRepository {
    override suspend fun getDictSportNormative(id: Int): MutableMap<String, MutableMap<String, Double>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalPoints(id: Int): Double {
        TODO("Not yet implemented")
    }

    override suspend fun changeResult(sport: String, oldNormative: String, newNormative: String) {
        TODO("Not yet implemented")
    }
}