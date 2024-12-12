package com.pablok.kinopoisklight.core

import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail

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
}