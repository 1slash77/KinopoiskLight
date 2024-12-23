package com.pablok.kinopoisklight.network

import com.pablok.kinopoisklight.network.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("movie/random")
    suspend fun getRandomTitle(): String

    @GET("movie")
    suspend fun getMovie(
        @Query("id") id: Int
    ): Response<MovieResponse>

    @GET("movie")
    suspend fun getRecentMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 21,
        @Query("type") type: String = "movie",
        @Query("year") year: String = "2024",
        @Query("rating.kp") raitingKp: String = "8-10",
        @Query("selectFields") selectedField: List<String> = listOf(
            "id",
            "name",
            "movieLength",
            "year",
            "description",
            "poster"
        )
    ): Response<MovieResponse>

}