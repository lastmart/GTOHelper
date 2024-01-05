package com.gtohelper.domain.di

import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.usecases.DeleteCompetitionByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideDeleteCompetitionByIdUseCase(
        competitionRepository: CompetitionRepository,
        disciplineRepository: DisciplineRepository
    ): DeleteCompetitionByIdUseCase {
        return DeleteCompetitionByIdUseCase(
            competitionRepository = competitionRepository
        )
    }
}