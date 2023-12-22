package com.gtohelper.data.di

import android.content.Context
import androidx.room.Room
import com.gtohelper.common.Mapper
import com.gtohelper.data.database.AppDatabase
import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.result.CompetitorResultsDao
import com.gtohelper.data.database.result.CompetitorResultsEntity
import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.data.database.sport.SportResultEntity
import com.gtohelper.data.mappers.CompetitorResultsEntityToCompetitorResultsMapper
import com.gtohelper.data.mappers.SportResultEntityToSportResultMapper
import com.gtohelper.data.repository.CompetitionRepositoryImpl
import com.gtohelper.data.repository.CompetitorRepositoryImpl
import com.gtohelper.data.repository.CompetitorResultsRepositoryImpl
import com.gtohelper.data.repository.DisciplineRepositoryImpl
import com.gtohelper.data.repository.SportRepositoryImpl
import com.gtohelper.domain.models.CompetitorResults
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.CompetitorResultsRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportRepository
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
    ): SportDao {
        return database.getSportDao()
    }

    @Provides
    @Singleton
    fun provideCompetitorResultsDao(
        database: AppDatabase
    ): CompetitorResultsDao {
        return database.getCompetitorResultsDao()
    }

    @Provides
    @Singleton
    fun provideCompetitionDao(database: AppDatabase): CompetitionDao {
        return database.getCompetitionDao()
    }

    @Provides
    @Singleton
    fun provideCompetitorResultsEntityToCompetitorResultsMapper(): Mapper<CompetitorResultsEntity, CompetitorResults> {
        return CompetitorResultsEntityToCompetitorResultsMapper()
    }

    @Provides
    @Singleton
    fun provideSportEntityToSportMapper(): Mapper<SportResultEntity, SportResult> {
        return SportResultEntityToSportResultMapper()
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
    fun provideCompetitorResultsRepository(
        dao: CompetitorResultsDao,
        mapper: Mapper<CompetitorResultsEntity, CompetitorResults>
    ): CompetitorResultsRepository {
        return CompetitorResultsRepositoryImpl(dao, mapper)
    }

    @Provides
    @Singleton
    fun provideSportRepository(
        dao: SportDao
    ): SportRepository {
        return SportRepositoryImpl(dao)
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
