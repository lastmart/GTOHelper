package com.gtohelper.domain.usecases

import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toDomainSubDiscipline
import com.gtohelper.domain.FinalExcelTableWriter
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.models.convertIntToSportResult
import com.gtohelper.domain.points_calculator.mapNormDisciplineToOfficialDisciplines
import com.gtohelper.domain.repository.CompetitorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

class DownloadResultTableUseCase(
    private val competitorRepository: CompetitorRepository,
    private val disciplineDao: DisciplineDao,
    private val finalExcelTableWriter: FinalExcelTableWriter
) {

    suspend operator fun invoke(
        competitionId: Int,
        competitionName: String,
        outputStream: OutputStream
    ) {

        withContext(Dispatchers.IO) {

            val competitors = competitorRepository.getCompetitionAllCompetitors(competitionId)

            val disciplinesWithCompetitorsWithResults =
                disciplineDao.getDisciplineWithCompetitorsWithResults()
                    .filter { subDisciplineWithCompetitorsWithResults ->
                        subDisciplineWithCompetitorsWithResults.competitorsWithResults.isNotEmpty()
                    }
                    .map { subDisciplineWithCompetitorsWithResults ->

                        val competitionSelectedSubDisciplines =
                            subDisciplineWithCompetitorsWithResults.competitorsWithResults.filter {
                                it.competitor.competitionId == competitionId
                            }

                        if (competitionSelectedSubDisciplines.isEmpty()) {
                            return@map null
                        }

                        return@map subDisciplineWithCompetitorsWithResults.copy(
                            competitorsWithResults = competitionSelectedSubDisciplines
                        )

                    }.filterNotNull()

            var currentDiscipline: SubDiscipline? = null

            val sportResultsMap = disciplinesWithCompetitorsWithResults.associateBy(
                keySelector = {
                    currentDiscipline = it.disciplineEntity.toDomainSubDiscipline()
                    mapNormDisciplineToOfficialDisciplines[it.disciplineEntity.name]!!
                },
                valueTransform = {
                    return@associateBy it.competitorsWithResults.associateBy(
                        keySelector = {
                            it.competitor.toDomainModel()
                        },
                        valueTransform = {
                            convertIntToSportResult(
                                currentDiscipline!!, it.sportResult.toDomainModel()
                            )
                        }
                    )
                }
            )

            finalExcelTableWriter.createFinalTable(
                nameTable = competitionName,
                fileOutputStream = outputStream,
                listCompetitor = competitors,
                sportResults = sportResultsMap
            )

            outputStream.close()
        }
    }
}