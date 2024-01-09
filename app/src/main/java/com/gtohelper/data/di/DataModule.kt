package com.gtohelper.data.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gtohelper.R
import com.gtohelper.data.database.AppDatabase
import com.gtohelper.data.database.Converters
import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplinesProvider
import com.gtohelper.data.database.relations.CompetitionSubDisciplineDao
import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.repository.CompetitionRepositoryImpl
import com.gtohelper.data.repository.CompetitorRepositoryImpl
import com.gtohelper.data.repository.CompetitorResultsRepositoryImpl
import com.gtohelper.data.repository.DisciplineRepositoryImpl
import com.gtohelper.data.repository.SportResultRepositoryImpl
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.CompetitorResultsRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportResultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val typeConverter = Converters()
                    val prePopulatedDisciplines = DisciplinesProvider().disciplines

                    prePopulatedDisciplines.forEach { discipline ->

                        val disciplineValue = ContentValues().apply {
                            put("name", discipline.name)
                            put("imageResource", discipline.imageResource)
                            put("type", typeConverter.fromDisciplinePointType(discipline.type))
                        }

                        db.insert(
                            table = "disciplines_table",
                            conflictAlgorithm = CONFLICT_REPLACE,
                            values = disciplineValue
                        )

                        discipline.subDisciplines.forEach { subDiscipline ->
                            val subDisciplineValue = ContentValues().apply {
                                put("parentName", discipline.name)
                                put("name", subDiscipline.name)
                                put("imageResource", subDiscipline.imageResource)
                                put(
                                    "type",
                                    typeConverter.fromDisciplinePointType(subDiscipline.type)
                                )
                            }

                            db.insert(
                                table = "sub_disciplines_table",
                                conflictAlgorithm = CONFLICT_REPLACE,
                                values = subDisciplineValue
                            )
                        }
                    }
                }
            })
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
    fun provideCompetitionDao(database: AppDatabase): CompetitionDao {
        return database.getCompetitionDao()
    }

    @Provides
    @Singleton
    fun provideDisciplineDao(database: AppDatabase): DisciplineDao {
        return database.getDisciplineDao()
    }

    @Provides
    @Singleton
    fun provideCompetitionDisciplineDao(database: AppDatabase): CompetitionSubDisciplineDao {
        return database.getCompetitionDisciplineDao()
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
    fun provideDisciplineRepository(
        dao: DisciplineDao,
        competitionDisciplineDao: CompetitionSubDisciplineDao
    ): DisciplineRepository {
        return DisciplineRepositoryImpl(dao, competitionDisciplineDao)
    }


    @Provides
    @Singleton
    fun provideSportResultDao(db: AppDatabase): SportResultDao {
        return db.getSportResultDao()
    }

    @Provides
    @Singleton
    fun provideSportResultRepository(
        dao: SportResultDao,
    ): SportResultRepository {
        return SportResultRepositoryImpl(dao)
    }


    @Provides
    @Named("json_string")
    @Singleton
    fun provideJsonString(
        @ApplicationContext context: Context
    ): String {
        return context
            .resources
            .openRawResource(R.raw.dictionary_with_standards)
            .bufferedReader()
            .use { it.readText() }
    }

    @Provides
    @Singleton
    fun provideCompetitorResultsRepository(
        sportResultDao: SportResultDao,
        competitorDao: CompetitorDao,
        @Named("json_string") jsonString: String,
        disciplineDao: DisciplineDao
    ): CompetitorResultsRepository {
        return CompetitorResultsRepositoryImpl(
            sportResultDao = sportResultDao,
            competitorDao = competitorDao,
            subDisciplineDao = disciplineDao,
            jsonString = jsonString,
        )
    }


    @Provides
    @Singleton
    fun provideCompetitionRepository(dao: CompetitionDao): CompetitionRepository {
        return CompetitionRepositoryImpl(dao)
    }
}
