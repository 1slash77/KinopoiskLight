package com.pablok.kinopoisklight.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie")
data class MovieLocal(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String,
)

@Entity("actor")
data class ActorLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
)