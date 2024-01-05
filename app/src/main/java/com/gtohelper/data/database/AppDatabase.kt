package com.gtohelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.database.competition.CompetitionEntity
import com.gtohelper.data.database.competitor.CompetitorDao
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.database.sport_result.SportResultEntity

@Database(
    version = 11,
    entities = [
        CompetitorEntity::class,
        SportResultEntity::class,
        CompetitionEntity::class,
        DisciplineEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCompetitorDao(): CompetitorDao

    abstract fun getSportResultDao(): SportResultDao

    abstract fun getCompetitionDao(): CompetitionDao

    abstract fun getDisciplineDao(): DisciplineDao
}