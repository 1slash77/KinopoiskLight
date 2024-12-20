package com.pablok.kinopoisklight.database.internal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pablok.kinopoisklight.database.dto.MovieLocal

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<MovieLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieLocal)

    @Delete
    suspend fun delete(movie: MovieLocal)
}