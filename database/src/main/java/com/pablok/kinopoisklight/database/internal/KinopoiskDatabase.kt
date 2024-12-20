package com.pablok.kinopoisklight.database.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pablok.kinopoisklight.database.dto.ActorLocal
import com.pablok.kinopoisklight.database.dto.MovieLocal
import com.pablok.kinopoisklight.database.internal.dao.ActorDao
import com.pablok.kinopoisklight.database.internal.dao.MovieDao

@Database(
    entities = [MovieLocal::class, ActorLocal::class],
    version = 1,
    exportSchema = true
)

abstract class KinopoiskDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
}