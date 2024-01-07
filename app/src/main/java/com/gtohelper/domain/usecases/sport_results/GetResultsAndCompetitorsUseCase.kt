package com.gtohelper.domain.usecases.sport_results

import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.SportResultRepository
import kotlinx.coroutines.flow.Flow

class GetResultsAndCompetitorsUseCase(
    private val sportResultRepository: SportResultRepository,
) {
    operator fun invoke(
        competitionId: Int,
        disciplineId: String,
    ): Flow<List<SportResultAndCompetitor>> {
        return sportResultRepository.getResultsAndCompetitors(
            competitionId = competitionId,
            disciplineId = disciplineId,
        )
    }
}
