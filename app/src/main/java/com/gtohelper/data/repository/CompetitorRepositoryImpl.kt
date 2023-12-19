package com.gtohelper.data.repository

import com.gtohelper.common.Mapper
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.repository.CompetitorRepository

class CompetitorRepositoryImpl(
    private val competitorDao: CompetitorDao,
    private val mapper: Mapper<CompetitorEntity, Competitor>
) : CompetitorRepository {

    override suspend fun getCompetitors(): List<Competitor> {
        return competitorDao.getCompetitors().map { mapper.transform(it) }
    }

    override suspend fun upsertCompetitor(competitor: Competitor) {
        val competitorEntity = competitorDao.getCompetitorById(competitor.participantNumber)

        val newCompetitorEntity = competitorEntity.copy(
            id = competitor.participantNumber,
            nameCompetitor = competitor.nameCompetitor,
            gender = Gender.valueOf(competitor.gender),
            nameTeam = competitor.nameTeam,
            degree = competitor.degree
        )

        competitorDao.upsertCompetitor(newCompetitorEntity)
    }

    override suspend fun changeCompetitor(oldCompetitor: Competitor, newCompetitor: Competitor) {
        val oldCompetitorEntity = competitorDao.getCompetitorById(oldCompetitor.participantNumber)
        competitorDao.deleteCompetitor(oldCompetitorEntity)

        val newCompetitorEntity = oldCompetitorEntity.copy(
            id = newCompetitor.participantNumber,
            nameCompetitor = newCompetitor.nameCompetitor,
            gender = Gender.valueOf(newCompetitor.gender),
            nameTeam = newCompetitor.nameTeam,
            degree = newCompetitor.degree
        )
        competitorDao.upsertCompetitor(newCompetitorEntity)
    }
}