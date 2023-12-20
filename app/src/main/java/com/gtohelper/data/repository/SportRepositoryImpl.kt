package com.gtohelper.data.repository

import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.domain.repository.SportRepository

class SportRepositoryImpl(
    private val sportDao: SportDao
) : SportRepository {
    override suspend fun getCompetitorIds(sport: String): List<Int> {
        return listOf()
       // return sportDao
       //     .getSportCompetitors(sport)
       //     .flatMap { it.competitorIds ?: emptyList() }
    }

    override suspend fun getSports(): List<String> {
        return sportDao.getSports().map { it.name }
    }

    override suspend fun removeCompetitor(sport: String, id: Int) {
        val sportCompetitors = getCompetitorIds(sport)

        val newSportCompetitors = sportCompetitors - id

        TODO()

    //    val newSportEntity = SportEntity(sport, newSportCompetitors)

    //    sportDao.upsertSport(newSportEntity)
    }

    override suspend fun addCompetitor(sport: String, id: Int) {
        val sportCompetitors = getCompetitorIds(sport)

        val newSportCompetitors = sportCompetitors + id

        TODO()

    //    val newSportEntity = SportEntity(sport, newSportCompetitors)

    //    sportDao.upsertSport(newSportEntity)
    }
}