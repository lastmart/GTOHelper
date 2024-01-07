package com.gtohelper.data.repository

import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toEntity
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.SportResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class SportResultRepositoryImpl(
    private val dao: SportResultDao,
) : SportResultRepository {

    override suspend fun getBy(id: Int): SportResult? {
        return withContext(Dispatchers.IO) {
            dao.getBy(id)?.toDomainModel()
        }
    }

    override suspend fun getBy(
        competitionId: Int,
        disciplineId: String,
        competitorId: Int
    ): SportResult? {
        return withContext(Dispatchers.IO) {
            dao.getBy(
                competitionId = competitionId,
                disciplineId = disciplineId,
                competitorId = competitorId
            )?.toDomainModel()
        }
    }

    override suspend fun create(sportResult: SportResult) {
        return withContext(Dispatchers.IO) {
            dao.create(sportResult.toEntity())
        }
    }

    override suspend fun update(sportResult: SportResult) {
        return withContext(Dispatchers.IO) {
            dao.update(sportResult.toEntity())
        }
    }

    override suspend fun delete(sportResult: SportResult) {
        return withContext(Dispatchers.IO) {
            dao.delete(sportResult.toEntity())
        }
    }

    override fun getResultsAndCompetitors(
        competitionId: Int, disciplineId: String
    ): Flow<List<SportResultAndCompetitor>> {
        return dao.getResultsAndCompetitors(
            competitionId,
            disciplineId,
        ).map { results -> results.map { it.toDomainModel() } }
    }

    override fun getCompetitionDisciplineResults(
        competitionId: Int, disciplineId: String
    ): Flow<List<SportResult>> {
        return dao.getCompetitionSportResults(competitionId, disciplineId).map {
            it.map { item -> item.toDomainModel() }
        }
    }
}