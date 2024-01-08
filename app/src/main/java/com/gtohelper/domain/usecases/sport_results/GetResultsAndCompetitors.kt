package com.gtohelper.domain.usecases.sport_results

import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.SportResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetResultsAndCompetitors(
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
