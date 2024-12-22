package com.pablok.kinopoisklight

import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.database.internal.DatabaseDataAdapter
import com.pablok.kinopoisklight.database.internal.dao.MovieDao
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.NetworkResponse
import com.pablok.kinopoisklight.network.dto.MovieResponse
import com.pablok.kinopoisklight.network.internal.NetworkDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


data class MoviesData(
    val movies: List<Movie>? = null,
    val errorMessage: String? = null,
)

class MoviesRepository @Inject constructor(
    private val api: KinopoiskApi,
    private val movieDao: MovieDao,
    private val adapterNet: NetworkDataAdapter,
    private val adapterDb: DatabaseDataAdapter
) {
    suspend fun getMovie(id: Int): MoviesData {

        return MoviesData()
    }

    suspend fun getRecentMovie(): MoviesData {
        val favorites = adapterDb.toDomain(movieDao.getMovies())
        val response = requestNetwork<MovieResponse> {
            api.getRecentMovies()
        }
        return toMoviesData(response, favorites)
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

    fun toMoviesData(response: NetworkResponse<MovieResponse>, favorites: List<Movie>): MoviesData {
        if (response is NetworkResponse.Success) {
            val movies = adapterNet.toDomain(response.data!!.docs.toList())
            return MoviesData(setFavorites(movies, favorites))
        } else if (response is NetworkResponse.Error) {
            return MoviesData(errorMessage = response.errorMsg)
        }
        return MoviesData()
    }

    private fun setFavorites(items: List<Movie>, favorites: List<Movie>): List<Movie> {
        return items.map { mv ->
            val fav = favorites.firstOrNull { it.id == mv.id }
            mv.copy(isFavorite = fav != null)
        }
    }

    suspend fun getFavorites() = withContext(Dispatchers.IO) {
        adapterDb.toDomain(movieDao.getMovies()).map {
            it.copy(isFavorite = true)
        }
    }

    suspend fun updateFavoriteState(movie: Movie, newState: Boolean) = withContext(Dispatchers.IO) {
        movie.isFavorite = newState;
        if (newState) {
            movieDao.insert(adapterDb.fromDomain(movie))
        } else {
            movieDao.delete((adapterDb.fromDomain(movie)))
        }
    }

}

