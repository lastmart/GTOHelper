package com.gtohelper.data.repository

import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.domain.PointsCalculator
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.CompetitorResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CompetitorResultsRepositoryImpl(
    private val sportResultDao: SportResultDao,
    private val competitorDao: CompetitorDao
) : CompetitorResultsRepository {
    override fun getCompetitorsWithSportResults(competitionId: Int): Flow<List<Pair<Competitor, Int>>> {
        return competitorDao.getCompetitorsWithSportResults(competitionId)
            .map { list ->
                list.map { (competitor, sportResults) ->

                    val totalPoints = sportResults
                        .sumOf {
                            getTotalPoints(
                                competitor = competitor.toDomainModel(),
                                sportResult = it.toDomainModel(),
                            )
                        }

                    Pair(competitor.toDomainModel(), totalPoints)
                }
            }
    }

    override suspend fun getTotalPoints(
        competitor: Competitor,
        sportResult: SportResult,
    ): Int {
        val pointsCalculator = PointsCalculator()

        // TODO
        return if (competitor.gender == Gender.FEMALE) -sportResult.value else sportResult.value
        //return pointsCalculator.getPoint(
        //    competitor = competitor,
        //    sport = sportName,
        //    result = result.toDouble()
        //)
    }
}