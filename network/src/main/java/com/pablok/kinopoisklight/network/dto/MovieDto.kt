package com.pablok.kinopoisklight.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val docs: Array<MovieNet>
)

@Serializable
data class MovieNet(
    val id: Int,
    val movieLength: Int,
    val year: Int,
    val name: String,
    val description: String,
    val poster: MoviePoster
)

@Serializable
data class MoviePoster(
    val url: String,
    val previewUrl: String
)