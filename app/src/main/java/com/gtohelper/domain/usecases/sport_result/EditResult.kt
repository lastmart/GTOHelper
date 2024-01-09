package com.gtohelper.domain.usecases.sport_result

import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.SportResultRepository

sealed class EditSportResultResult {
    data object CompetitorDoesNotExistInCompetition : EditSportResultResult()
    data object ResultDoesNotExist : EditSportResultResult()
    data object ResultAlreadyExists: EditSportResultResult()
    data object Success : EditSportResultResult()
}

class EditSportResultUseCase(
    private val sportResultRepository: SportResultRepository,
    private val competitorRepository: CompetitorRepository,
) {
    suspend operator fun invoke(
        number: Int,
        value: Int,
        resultId: Int,
        competitionId: Int,
    ): EditSportResultResult {
        val newCompetitor = competitorRepository.getBy(number, competitionId)
            ?: return EditSportResultResult.CompetitorDoesNotExistInCompetition

        val oldResult = sportResultRepository.getBy(resultId)
            ?: return EditSportResultResult.ResultDoesNotExist
        val existingResult =
            sportResultRepository.getBy(competitionId, oldResult.disciplineId, newCompetitor.id)

        return if (existingResult == null || existingResult.id == oldResult.id) {
            sportResultRepository.update(
                oldResult.copy(
                    value = value,
                    competitorId = newCompetitor.id,
                )
            )

            EditSportResultResult.Success
        } else {
            EditSportResultResult.ResultAlreadyExists
        }
    }
}