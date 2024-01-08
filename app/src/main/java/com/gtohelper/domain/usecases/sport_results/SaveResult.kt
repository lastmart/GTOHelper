package com.gtohelper.domain.usecases.sport_results

import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.SportResultRepository

sealed class SaveSportResultResult {
    data object CompetitorDoesNotExist : SaveSportResultResult()
    data object ResultAlreadyExists : SaveSportResultResult()
    data object Success : SaveSportResultResult()
}

class SaveSportResultUseCase(
    private val sportResultRepository: SportResultRepository,
    private val competitorRepository: CompetitorRepository,
) {
    suspend operator fun invoke(
        number: Int,
        value: Int,
        competitionId: Int,
        disciplineId: String
    ): SaveSportResultResult {
        val competitor = competitorRepository.getBy(number, competitionId)
        if (competitor != null) {
            return if (sportResultRepository.getBy(
                    competitionId, disciplineId, competitor.id
                ) == null
            ) {
                val result = SportResult(
                    sportName = disciplineId,
                    competitionId = competitionId,
                    value = value,
                    competitorId = competitor.id
                )
                sportResultRepository.create(result)
                SaveSportResultResult.Success
            } else {
                SaveSportResultResult.ResultAlreadyExists
            }

        } else {
            return SaveSportResultResult.CompetitorDoesNotExist
        }
    }
}