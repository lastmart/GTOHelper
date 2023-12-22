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
    private val competitorDao: CompetitorDao,
) : CompetitorRepository {
    override fun getCompetitionCompetitors(competitionId: Int): Flow<List<Competitor>> {
        return competitorDao.getCompetitionCompetitors(competitionId).map { list ->
            list.map { it.toDomainModel() }
        }
    }

    override suspend fun create(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            competitorDao.create(competitor.toEntity())
        }
    }

    override suspend fun update(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            competitorDao.update(competitor.toEntity())
        }
    }

    override suspend fun delete(competitor: Competitor) {
        withContext(Dispatchers.IO) {
            competitorDao.delete(competitor.toEntity())
        }
    }

}