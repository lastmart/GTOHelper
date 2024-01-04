package com.gtohelper.data.di

import android.content.Context
import androidx.room.Room
import com.gtohelper.data.database.AppDatabase
import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.repository.CompetitionRepositoryImpl
import com.gtohelper.data.repository.CompetitorRepositoryImpl
import com.gtohelper.data.repository.DisciplineRepositoryImpl
import com.gtohelper.data.repository.SportResultRepositoryImpl
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportResultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "gto_helper_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCompetitorDao(
        database: AppDatabase
    ): CompetitorDao {
        return database.getCompetitorDao()
    }

    @Provides
    @Singleton
    fun provideSportDao(
        database: AppDatabase
    ): SportResultDao {
        return database.getSportResultDao()
    }

    @Provides
    @Singleton
    fun provideCompetitionDao(database: AppDatabase): CompetitionDao {
        return database.getCompetitionDao()
    }

    @Provides
    @Singleton
    fun provideCompetitorRepository(
        dao: CompetitorDao,
    ): CompetitorRepository {
        return CompetitorRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSportResultRepository(
        dao: SportResultDao
    ): SportResultRepository {
        return SportResultRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDisciplineRepository(): DisciplineRepository {
        return DisciplineRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideCompetitionRepository(dao: CompetitionDao): CompetitionRepository {
        return CompetitionRepositoryImpl(dao)
    }
}
