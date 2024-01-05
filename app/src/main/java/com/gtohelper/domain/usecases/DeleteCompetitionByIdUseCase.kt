package com.gtohelper.domain.usecases

import com.gtohelper.domain.repository.CompetitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteCompetitionByIdUseCase(
    private val competitionRepository: CompetitionRepository
) {

    suspend operator fun invoke(id: Int) {
        withContext(Dispatchers.IO) {
            competitionRepository.deleteById(id)
        }
    }
}