package com.gtohelper.data.repository

import com.gtohelper.common.Mapper
//import com.gtohelper.data.database.result.CompetitorResultsDao
//import com.gtohelper.data.database.result.CompetitorResultsEntity
import com.gtohelper.domain.models.CompetitorResults
import com.gtohelper.domain.repository.CompetitorResultsRepository

class CompetitorResultsRepositoryImpl(
//    private val competitorResultsDao: CompetitorResultsDao,
//    private val mapper: Mapper<CompetitorResultsEntity, CompetitorResults>
) : CompetitorResultsRepository {
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