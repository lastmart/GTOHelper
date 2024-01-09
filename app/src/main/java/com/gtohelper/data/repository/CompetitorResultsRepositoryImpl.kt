package com.gtohelper.data.repository

import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toDomainSubDiscipline
import com.gtohelper.domain.PointsCalculator
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.models.LongDuration
import com.gtohelper.domain.models.ShortDuration
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.repository.CompetitorResultsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class CompetitorResultsRepositoryImpl(
    private val competitorDao: CompetitorDao,
    private val subDisciplineDao: DisciplineDao,
    private val jsonString: String
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

        return withContext(Dispatchers.IO) {

            val discipline = subDisciplineDao.getSubDisciplineById(sportResult.disciplineId)
                ?: return@withContext 0


            return@withContext try {

                val sportResultValue: Any = convertIntToSportResult(
                    discipline = discipline.toDomainSubDiscipline(),
                    sportResult = sportResult
                )

                pointsCalculator.getPoint(
                    competitor = competitor,
                    sport = discipline.name,
                    result = sportResultValue as Comparable<Any>,
                    jsonString = jsonString
                )
            } catch (e: Exception) {
                return@withContext 0
            }
        }
    }
}

fun convertIntToSportResult(discipline: SubDiscipline, sportResult: SportResult): Any {
    return when (discipline.type) {
        DisciplinePointType.SHORT_TIME -> {
            val shortDuration = ShortDuration.fromMillis(sportResult.value)

            convertRunTime(
                time = "${shortDuration.seconds}.${shortDuration.deciSeconds}"
            )
        }

        DisciplinePointType.LONG_TIME -> {
            val longDuration = LongDuration.fromMillis(sportResult.value)
            LocalTime.of(longDuration.hours, longDuration.minutes, longDuration.seconds)
        }

        DisciplinePointType.AMOUNT -> sportResult.value.toDouble()
    }
}

fun convertRunTime(time: String): LocalTime {
    var changeTime = time
    if ("." !in time) {
        changeTime = "$time.0"
    }
    val seconds = changeTime.split(".")[0]
    val milliseconds = changeTime.split(".")[1]
    val time =
        "00:${if (seconds.length == 1) "0$seconds" else seconds}.${if (milliseconds.length == 1) "${milliseconds}0" else milliseconds}"
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("mm:ss.SS")
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .toFormatter()
    return LocalTime.parse(time, formatter)
} // TODO needs refactoring