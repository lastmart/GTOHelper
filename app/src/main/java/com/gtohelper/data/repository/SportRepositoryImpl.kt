package com.gtohelper.data.repository

import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.domain.repository.SportRepository

class SportRepositoryImpl(
    private val sportDao: SportDao
) : SportRepository {
    override suspend fun getCompetitorIds(sport: String): List<Int> {
        return sportDao
            .getSportCompetitors(sport)
            .flatMap { it.competitorIds ?: emptyList() }
    }

    override suspend fun getSports(): List<String> {
        return sportDao.getSports().map { it.name }
    }

    override suspend fun removeCompetitor(id: Int) {
        TODO("???")
    }

    override suspend fun addCompetitor(id: Int) {
        TODO("Not yet implemented")
    }
}