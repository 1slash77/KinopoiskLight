package com.pablok.kinopoisklight.network.internal

import com.pablok.kinopoisklight.core.BaseDomainAdapter
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail
import com.pablok.kinopoisklight.network.dto.MovieNet
import com.pablok.kinopoisklight.network.dto.MoviePoster
import javax.inject.Inject

class NetworkDataAdapter @Inject constructor(): BaseDomainAdapter<Movie, MovieNet>() {
    override fun fromDomain(domain: Movie): MovieNet =
        MovieNet(
            id = domain.id,
            movieLength = 0,
            year = 0,
            name = domain.title,
            description = "",
            poster = MoviePoster(
                url = domain.thumbnail.path,
                previewUrl = domain.thumbnail.path
            )
        )

    override fun toDomain(another: MovieNet): Movie =
        Movie(
            id = another.id,
            title = another.name,
            thumbnail = Thumbnail(
                path = if (another.poster != null) another.poster.previewUrl else "",
                extension = ""
            ),
            isFavorite = false
        )
}