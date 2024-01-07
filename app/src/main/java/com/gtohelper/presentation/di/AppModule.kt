package com.gtohelper.presentation.di

import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.SportResultRepository
import com.gtohelper.domain.usecases.sport_results.GetResultsAndCompetitorsUseCase
import com.gtohelper.domain.usecases.sport_results.SaveSportResultUseCase
import com.gtohelper.domain.usecases.sport_results.SportResultUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideSportResultUseCases(
        sportResultRepository: SportResultRepository,
        competitorRepository: CompetitorRepository,
    ): SportResultUseCases {
        return SportResultUseCases(
            saveSportResult = SaveSportResultUseCase(
                sportResultRepository = sportResultRepository,
                competitorRepository = competitorRepository
            ),
            getResultsAndCompetitors = GetResultsAndCompetitorsUseCase(
                sportResultRepository = sportResultRepository
            )
        )
    }
}