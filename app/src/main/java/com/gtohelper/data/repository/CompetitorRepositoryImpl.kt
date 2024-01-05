package com.gtohelper.data.repository


import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toEntity
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CompetitorRepositoryImpl(
    private val dao: CompetitorDao,
) : CompetitorRepository {
    override fun getCompetitionCompetitors(competitionId: Int): Flow<List<Competitor>> {
        return dao.getCompetitionCompetitors(competitionId).map { list ->
            list.map { it.toDomainModel() }
        }
    }

    override fun getCompetitionCompetitorCount(competitionId: Int): Flow<Int> {
        return dao.getCompetitionCompetitorCount(competitionId)
    }

    override suspend fun getById(competitorId: Int): Competitor? {
        return withContext(Dispatchers.IO) {
            dao.getCompetitorById(competitorId)?.toDomainModel()
        }
    }

    override suspend fun getByNumberInCompetition(
        competitorNumber: Int,
        competitionId: Int
    ): Competitor? {
        return withContext(Dispatchers.IO) {
            dao.getCompetitorByNumberInCompetition(competitorNumber, competitionId)?.toDomainModel()
        }
    }

    override suspend fun create(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            dao.create(competitor.toEntity())
        }
    }

    override suspend fun update(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            dao.update(competitor.toEntity())
        }
    }

    override suspend fun delete(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            dao.delete(competitor.toEntity())
        }
    }
}