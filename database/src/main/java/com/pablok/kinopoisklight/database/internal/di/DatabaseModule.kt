package com.pablok.kinopoisklight.database.internal.di

import android.content.Context
import androidx.room.Room
import com.pablok.kinopoisklight.database.internal.KinopoiskDatabase
import com.pablok.kinopoisklight.database.internal.dao.ActorDao
import com.pablok.kinopoisklight.database.internal.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KinopoiskDatabase = Room.databaseBuilder(context, KinopoiskDatabase::class.java, "kinopoisk.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideMovieDao(
        database: KinopoiskDatabase
    ): MovieDao = database.movieDao()

    @Provides
    fun provideComicDao(
        database: KinopoiskDatabase
    ): ActorDao = database.actorDao()
}