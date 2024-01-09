package com.gtohelper.domain.usecases.sport_result

import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.SportResultRepository
import kotlinx.coroutines.flow.Flow

class GetResultsAndCompetitors(
    private val sportResultRepository: SportResultRepository,
) {
    operator fun invoke(
        competitionId: Int,
        disciplineId: Int,
    ): Flow<List<SportResultAndCompetitor>> {
        return sportResultRepository.getResultsAndCompetitors(
            competitionId = competitionId,
            disciplineId = disciplineId,
        )
    }
}
