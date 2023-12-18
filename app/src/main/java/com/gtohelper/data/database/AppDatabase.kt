package com.gtohelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.result.ResultDao
import com.gtohelper.data.database.result.CompetitorResultsEntity
import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.data.database.sport.SportEntity

@Database(
    version = 1,
    entities = [
        CompetitorEntity::class,
        CompetitorResultsEntity::class,
        SportEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCompetitorDao(): CompetitorDao

    abstract fun getResultDao(): ResultDao

    abstract fun getSportDao(): SportDao
}