package com.pablok.kinopoisklight.database.internal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pablok.kinopoisklight.database.dto.ActorLocal

@Dao
interface ActorDao {
    @Query("SELECT * FROM Actor")
    suspend fun getActors(): List<ActorLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Actor: ActorLocal)

    @Delete
    suspend fun delete(Actor: ActorLocal)
}