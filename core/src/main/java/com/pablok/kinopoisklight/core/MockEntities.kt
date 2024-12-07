package com.pablok.kinopoisklight.core

import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail

object MockEntitis  {
    fun mockCharacter() = Actor(
        id = 0,
        name = "Spider-Man",
        thumbnail = mockThumbnail()
    )

    fun mockComic() = Movie(
        id = 0,
        title = "SPIDER-BOY ANNUAL #1",
        thumbnail = mockThumbnail()
    )

    fun mockThumbnail() = Thumbnail(
        path = "http://icon",
        extension = "jpg"
    )
}