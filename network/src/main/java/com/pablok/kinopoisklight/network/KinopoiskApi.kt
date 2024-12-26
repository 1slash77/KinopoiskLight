package com.pablok.kinopoisklight.network

import com.pablok.kinopoisklight.network.dto.ActorDetailsNet
import com.pablok.kinopoisklight.network.dto.MovieDetailsResponse
import com.pablok.kinopoisklight.network.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("movie/random")
    suspend fun getRandomTitle(): String

    @GET("movie")
    suspend fun getRecentMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String = "movie",
        @Query("year") year: String = "2024",
        @Query("rating.imdb") raitingKp: String = "7-10",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("notNullFields") notNullFields: List<String> = listOf(
            "name",
            ),
        @Query("selectFields") selectedField: List<String> = listOf(
            "id",
            "name",
            "movieLength",
            "year",
            "description",
            "poster"
        )
    ): Response<MovieResponse>

    @GET("movie/search")
    suspend fun searchMovie(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String,
        //@Query("type") type: String = "movie",
/*        @Query("notNullFields") notNullFields: List<String> = listOf(
            "name",
            "poster.url",
            "year",
            "description",
        ),*/
/*        @Query("selectFields") selectedField: List<String> = listOf(
            "id",
            "name",
            "movieLength",
            "year",
            "description",
            "poster"
        )*/
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<MovieDetailsResponse>

    @GET("person/{id}")
    suspend fun getActor(
        @Path("id") id: Int
    ): Response<ActorDetailsNet>

}