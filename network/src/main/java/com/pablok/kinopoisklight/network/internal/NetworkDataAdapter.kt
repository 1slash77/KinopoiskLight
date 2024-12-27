package com.pablok.kinopoisklight.network.internal

import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.core.dto.ActorDetails
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.MovieDetails
import com.pablok.kinopoisklight.core.dto.Thumbnail
import com.pablok.kinopoisklight.network.dto.ActorDetailsNet
import com.pablok.kinopoisklight.network.dto.ArrayValue
import com.pablok.kinopoisklight.network.dto.MovieDetailsResponse
import com.pablok.kinopoisklight.network.dto.MovieNet
import com.pablok.kinopoisklight.network.dto.MoviePoster
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

class NetworkDataAdapter @Inject constructor() {
    fun fromDomain(domain: Movie): MovieNet =
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
    fun fromDomain(domainList: List<Movie>): List<MovieNet> {
        return domainList.map {
            fromDomain(it)
        }
    }

/*    fun fromDomain(domain: MovieDetails): MovieDetailsResponse =
        MovieDetailsResponse(
            id = domain.id,
            movieLength = domain.movieLength,
            year = domain.year,
            name = domain.name,
            description = domain.description,
            poster = MoviePoster(domain.posterUrl, null),
            persons = domain.persons.map { ActorNet() },
        )*/

    fun toDomain(another: MovieNet): Movie =
        Movie(
            id = another.id,
            title = another.name,
            thumbnail = Thumbnail(
                path = if (another.poster != null) another.poster.previewUrl else "",
                extension = ""
            ),
            isFavorite = false
        )

    fun toDomain(anotherList: List<MovieNet>): List<Movie> {
        return anotherList.map {
            toDomain(it)
        }
    }

    fun toDomain(another: MovieDetailsResponse) = MovieDetails(
        id = another.id,
        name = another.name,
        movieLength = another.movieLength,
        year = another.year,
        description = another.description,
        posterUrl = another.poster?.url,
        persons = another.persons?.toList()?.filter { it.name?.isNotEmpty()?:false }?.map {
            Actor(
                id = it.id,
                name = it.name?:"",
                character = it.description?:"",
                photoUrl = it.photo,
                isFavorite = false
            )
        } ?: emptyList(),
        isFavorite = false
    )

    fun fromDomain(domain: ActorDetails) = ActorDetailsNet(
        id = domain.id,
        name = domain.name,
        photo = domain.photoUrl,
        profession = domain.profession.map {ArrayValue(it) }.toTypedArray(),
        birthday = domain.birhtday?.let { SimpleDateFormat("dd MMM yyyy").format(it) },
        facts = domain.facts.map {ArrayValue(it) }.toTypedArray(),
    )

    fun toDomain(another:ActorDetailsNet) = ActorDetails(
        id = another.id,
        name = another.name,
        photoUrl = fixUrl(another.photo),
        profession = another.profession.map { it.value }.toList(),
        birhtday = parseDate(another.birthday),
        facts = another.facts.filter { !it.value.contains("href") }.map { it.value }.toList(),
    )

    private fun parseDate(s: String?): Date? {
        try {
            val ldt = LocalDateTime.parse(s!!.replace("Z", ""))
            val zdt: ZonedDateTime = ldt.atZone(ZoneId.of("America/Los_Angeles"))
            return Date.from(zdt.toInstant())
        } catch (_: Exception) {
            return null
        }
    }

    private fun fixUrl(s: String?): String? {
        if (s!=null)
        return s.replace("https:https:", "https:")
        else return null
    }
}