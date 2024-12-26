package com.pablok.kinopoisklight.core

import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.core.dto.ActorDetails
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.MovieDetails
import com.pablok.kinopoisklight.core.dto.Thumbnail
import kotlin.random.Random

object MockEntities  {
    fun mockActor() = Actor(
        id = 0,
        name = "Bruce",
        character = "MainHero",
        photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Matthew_McConaughey_2019_%2848648344772%29.jpg/1920px-Matthew_McConaughey_2019_%2848648344772%29.jpg"
    )

    fun mockMovie() = Movie(
        id = 0,
        title = "Interstellar",
        thumbnail = mockThumbnail()
    )

    fun mockThumbnail() = Thumbnail(
        path = "https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg",
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

    fun mockMovieDetails() = MovieDetails(
        id = 0,
        name = "Интерстеллар",
        movieLength = 169,
        year = 2012,
        description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg",
        persons = List<Actor>(21) { i -> mockActor().copy(
            name = "Имя актера $i",
            character = "Имя героя $i"
        ) },
    )

    fun mockActorDetails() = ActorDetails(
        id = 0,
        name = "Bruce",
        photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Matthew_McConaughey_2019_%2848648344772%29.jpg/1920px-Matthew_McConaughey_2019_%2848648344772%29.jpg",
        profession = listOf("Актер", "Сценарист", "Продюсер"),
        birhtday = "1962-01-01T00:00:00.000Z"
    )
}