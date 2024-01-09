package com.gtohelper.domain.usecases.sport_result

import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.SportResultRepository

class DeleteSportResultUseCase(
    private val sportResultRepository: SportResultRepository
) {

    suspend operator fun invoke(sportResult: SportResult) {
        sportResultRepository.delete(sportResult)
    }
}
