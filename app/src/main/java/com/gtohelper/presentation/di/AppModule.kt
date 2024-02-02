package com.gtohelper.presentation.di

import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.domain.FinalExcelTableWriter
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.SportResultRepository
import com.gtohelper.domain.usecases.DownloadResultTableUseCase
import com.gtohelper.domain.usecases.sport_result.DeleteSportResultUseCase
import com.gtohelper.domain.usecases.sport_result.EditSportResultUseCase
import com.gtohelper.domain.usecases.sport_result.GetResultsAndCompetitors
import com.gtohelper.domain.usecases.sport_result.SaveSportResultUseCase
import com.gtohelper.domain.usecases.sport_result.SportResultUseCases
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
            deleteSportResult = DeleteSportResultUseCase(
                sportResultRepository = sportResultRepository,
            ),
            editSportResult = EditSportResultUseCase(
                sportResultRepository = sportResultRepository,
                competitorRepository = competitorRepository
            ),
            getResultsAndCompetitors = GetResultsAndCompetitors(
                sportResultRepository = sportResultRepository
            ),
        )
    }

    @Provides
    fun provideDownloadResultTableUseCase(
        competitorRepository: CompetitorRepository,
        disciplineDao: DisciplineDao,
        finalExcelTableWriter: FinalExcelTableWriter
    ): DownloadResultTableUseCase {
        return DownloadResultTableUseCase(
            competitorRepository = competitorRepository,
            disciplineDao = disciplineDao,
            finalExcelTableWriter = finalExcelTableWriter
        )
    }
}