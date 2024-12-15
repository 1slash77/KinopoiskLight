package com.pablok.kinopoisklight.network

import com.pablok.kinopoisklight.network.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("movie/random")
    suspend fun getRandomTitle(): String

    @GET("movie")
    suspend fun getMovie(@Query("id") id: Int): MovieResponse
}