package com.pablok.kinopoisklight.core.dto

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
    val persons: List<Actor>
) {
    override fun toString(): String {
        return "${super.toString()} persons:\n${persons.joinToString("\n\t")}"
    }
}
