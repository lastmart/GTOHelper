package com.gtohelper.domain.usecases

import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteCompetitionByIdUseCase(
    private val competitionRepository: CompetitionRepository,
    private val disciplineRepository: DisciplineRepository
) {

    suspend operator fun invoke(id: Int) {
        withContext(Dispatchers.IO) {
            competitionRepository.deleteById(id)
            // TODO add all disciplines, competitors, results of this competition
        }
    }
}