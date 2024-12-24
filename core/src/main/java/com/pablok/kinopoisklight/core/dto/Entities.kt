package com.pablok.kinopoisklight.core.dto

data class Actor(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
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