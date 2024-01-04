package com.gtohelper.data.repository

import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toEntity
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.SportResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class SportResultRepositoryImpl(
    private val dao: SportResultDao,
) : SportResultRepository {

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

    override fun getCompetitionDisciplineResults(
        competitionId: Int,
        disciplineId: String
    ): Flow<List<SportResult>> {
        return dao.getCompetitionSportResults(competitionId, disciplineId).map {
            it.map { item -> item.toDomainModel() }
        }
    }
}