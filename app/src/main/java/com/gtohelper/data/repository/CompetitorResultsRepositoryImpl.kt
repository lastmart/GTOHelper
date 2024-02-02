package com.gtohelper.data.repository

import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toDomainSubDiscipline
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.convertIntToSportResult
import com.gtohelper.domain.points_calculator.PointsCalculator
import com.gtohelper.domain.repository.CompetitorResultsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CompetitorResultsRepositoryImpl(
    private val competitorDao: CompetitorDao,
    private val subDisciplineDao: DisciplineDao,
    private val pointsCalculator: PointsCalculator
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
        return withContext(Dispatchers.IO) {

            val discipline = subDisciplineDao.getSubDisciplineById(sportResult.disciplineId)
                ?: return@withContext 0


            return@withContext try {

                val sportResultValue: Any = convertIntToSportResult(
                    discipline = discipline.toDomainSubDiscipline(),
                    sportResult = sportResult
                )

                pointsCalculator.getPoints(
                    competitor = competitor,
                    sport = discipline.name,
                    result = sportResultValue as Comparable<Any>,
                )
            } catch (e: Exception) {
                return@withContext 0
            }
        }
    }
}