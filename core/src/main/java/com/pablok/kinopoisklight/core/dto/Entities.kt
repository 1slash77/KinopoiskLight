package com.pablok.kinopoisklight.core.dto

import java.util.Date

data class Actor(
    val id: Int,
    val name: String,
    val character: String,
    val photoUrl: String?,
    var isFavorite: Boolean = false
)

data class Movie(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    var isFavorite: Boolean = false
)

data class Thumbnail(
    val path: String?,
    val extension: String
)

data class MovieDetails(
    val id: Int,
    val name: String,
    val movieLength: Int?,
    val year: Int,
    val description: String?,
    val posterUrl: String?,
    val persons: List<Actor>,
    val isFavorite: Boolean = false
) {
    override fun toString(): String {
        return "${super.toString()} persons:\n${persons.joinToString("\n\t")}"
    }
}

data class ActorDetails(
    val id: Int,
    val name: String?,
    val photoUrl: String?,
    val profession: List<String>,
    val birhtday: Date?,
    val facts: List<String>,
    val isFavorite: Boolean = false
)
