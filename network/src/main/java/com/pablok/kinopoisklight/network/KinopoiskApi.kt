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
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("year") year: String,
        @Query("rating.kp") raitingKp: String,
    ): Response<MovieResponse>

    //.url("https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&type=movie&year=2024&rating.kp=8-10")
}