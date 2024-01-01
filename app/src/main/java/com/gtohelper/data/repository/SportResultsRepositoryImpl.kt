package com.gtohelper.data.repository

import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.SportResultsRepository
import kotlinx.coroutines.flow.Flow


class SportResultsRepositoryImpl : SportResultsRepository {

    private val sportResults: List<SportResult> = listOf()


    override suspend fun create(sportResult: SportResult) {
        TODO("Not yet implemented")
    }

    override suspend fun update(sportResult: SportResult) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(sportResult: SportResult) {
        TODO("Not yet implemented")
    }

    override fun getCompetitionDisciplineResults(
        competitionId: Int,
        disciplineId: String
    ): Flow<List<SportResult>> {
        TODO("Not yet implemented")
    }
}