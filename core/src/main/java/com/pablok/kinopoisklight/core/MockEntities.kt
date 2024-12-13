package com.pablok.kinopoisklight.core

import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail
import kotlin.random.Random

object MockEntitis  {
    fun mockActor() = Actor(
        id = 0,
        name = "Bruce",
        thumbnail = mockThumbnail()
    )

    fun mockMovie() = Movie(
        id = 0,
        title = "Interstellar",
        thumbnail = mockThumbnail()
    )

    fun mockThumbnail() = Thumbnail(
        path = "https://upload.wikimedia.org/wikipedia/ru/c/c3/Interstellar_2014",
        extension = "jpg"
    )

    fun mockMovies(): List<Movie> {
        val offset = (0..100).random()
        val movies: MutableList<Movie> = mutableListOf()
        repeat(21) { n ->
            movies.add(
                Movie(
                    id = offset + n,
                    title = "Interstellar ${offset + n}",
                    thumbnail = mockThumbnail(),
                    isFavorite = Random.nextBoolean()
                )
            )
        }
        return movies.toList()
    }
}