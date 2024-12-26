package com.pablok.kinopoisklight.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val docs: Array<MovieNet>
)

@Serializable
data class MovieNet(
    val id: Int,
    val movieLength: Int?,
    val year: Int,
    val name: String,
    val description: String?,
    val poster: MoviePoster?
)

@Serializable
data class MoviePoster(
    val url: String?,
    val previewUrl: String?
)

@Serializable
data class MovieDetailsResponse(
    val id: Int,
    val name: String,
    val movieLength: Int?,
    val year: Int,
    val description: String?,
    val poster: MoviePoster?,
    val persons: Array<ActorNet>?
) {
    override fun toString(): String {
        return "${super.toString()} persons:\n${persons?.joinToString("\n\t")}"
    }
}

@Serializable
data class ActorNet(
    val id: Int,
    val photo: String?,
    val name: String?,
    val description: String?,
)

@Serializable
data class ActorDetailsNet(
    val id: Int,
    val name: String?,
    val photo: String?,
    val profession: Array<ActorProfession>,
    val birhtday: String?
)

@Serializable
data class ActorProfession(
    val value: String
)