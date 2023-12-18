package com.gtohelper.data.repository

import com.gtohelper.domain.repository.SportRepository

class SportRepositoryImpl : SportRepository {
    override suspend fun getCompetitorIds(sport: String): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getSports(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun removeCompetitor(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addCompetitor(id: Int) {
        TODO("Not yet implemented")
    }
}