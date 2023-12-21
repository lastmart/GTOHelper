package com.gtohelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.result.CompetitorResultsDao
import com.gtohelper.data.database.result.CompetitorResultsEntity
import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.data.database.sport.SportEntity

@Database(
    version = 1,
    entities = [
        CompetitorEntity::class,
        CompetitorResultsEntity::class,
        SportEntity::class,
        CompetitionEntity::class,
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCompetitorDao(): CompetitorDao

    abstract fun getCompetitorResultsDao(): CompetitorResultsDao

    abstract fun getSportDao(): SportDao

    abstract fun getCompetitionDao(): CompetitionDao
}