package com.pablok.kinopoisklight.database.internal

import com.pablok.kinopoisklight.core.BaseDomainAdapter
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail
import com.pablok.kinopoisklight.database.dto.MovieLocal
import javax.inject.Inject

class DatabaseDataAdapter @Inject constructor(): BaseDomainAdapter<Movie, MovieLocal>() {
    override fun fromDomain(domain: Movie): MovieLocal =
        MovieLocal(
            id = domain.id,
            title = domain.title,
            image = domain.thumbnail.path,
        )

    override fun toDomain(another: MovieLocal): Movie =
        Movie(
            id = another.id,
            title = another.title,
            thumbnail = Thumbnail(
                path = another.image,
                extension = ""
            ),
            isFavorite = false
        )
}