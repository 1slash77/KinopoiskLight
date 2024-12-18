package com.pablok.kinopoisklight

import android.util.Log
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.Thumbnail
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.NetworkResponse
import com.pablok.kinopoisklight.network.dto.MovieResponse
import com.pablok.kinopoisklight.network.internal.NetworkDataAdapter
import retrofit2.Response
import javax.inject.Inject


data class ComicsData(
    val movies: List<Movie>? = null,
    val errorMessage: String? = null,
)

class MoviesRepository @Inject constructor(
    private val api: KinopoiskApi,
    //private val favoritesDao: ComicDao,
    private val adapter: NetworkDataAdapter
) {
    suspend fun getMovie(id: Int): ComicsData {

        return ComicsData()
    }

    suspend fun getRecentMovie(): ComicsData {
        val response = requestNetwork<MovieResponse> {
            api.getRecentMovies(
                page = 1,
                limit = 21,
                type = "movie",
                year = "2024",
                raitingKp = "8-10"
            )
        }
        return toComicsData(response)
    }


    inline fun <reified T> requestNetwork(
        executable: () -> Response<T>
    ): NetworkResponse<T> {
        try {
            val response = executable()
            if (response.isSuccessful) {
                val successBody = response.body()
                return NetworkResponse.Success(successBody)
            } else {
                return NetworkResponse.Error(response.errorBody()?.string())
            }
        } catch (throwable: Throwable) {
            return NetworkResponse.Error(throwable.message)
        }
    }

    fun toComicsData(response: NetworkResponse<MovieResponse>): ComicsData {
        if (response is NetworkResponse.Success) {
            val movies = adapter.toDomain(response.data!!.docs.toList())
            return ComicsData(movies)
        } else if (response is NetworkResponse.Error) {
            return ComicsData(errorMessage = response.errorMsg)
        }
        return ComicsData()
    }
}

