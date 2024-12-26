package com.pablok.kinopoisklight

import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.core.dto.MovieDetails
import com.pablok.kinopoisklight.database.internal.DatabaseDataAdapter
import com.pablok.kinopoisklight.database.internal.dao.MovieDao
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.NetworkResponse
import com.pablok.kinopoisklight.network.dto.MovieDetailsResponse
import com.pablok.kinopoisklight.network.dto.MovieResponse
import com.pablok.kinopoisklight.network.internal.NetworkDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


data class MoviesData(
    val movies: List<Movie>? = null,
    val errorMessage: String? = null,
)

data class MovieDetailsData(
    val movie: MovieDetails? = null,
    val errorMessage: String? = null,
)

class MoviesRepository @Inject constructor(
    private val api: KinopoiskApi,
    private val movieDao: MovieDao,
    private val adapterNet: NetworkDataAdapter,
    private val adapterDb: DatabaseDataAdapter
) {
    private val mock = false

    suspend fun getRecentMovie(): MoviesData {
        val favorites = adapterDb.toDomain(movieDao.getMovies())
        if (mock) {
            delay(200)
            val response = MovieResponse(
                docs = adapterNet.fromDomain(MockEntities.mockMovies()).toTypedArray()
            )
            return toMoviesData(NetworkResponse.Success(response), favorites)
        } else {
            val response = requestNetwork<MovieResponse> {
                api.getRecentMovies()
            }
            return toMoviesData(response, favorites)
        }
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

    private fun toMoviesData(response: NetworkResponse<MovieResponse>, favorites: List<Movie>): MoviesData {
        if (response is NetworkResponse.Success) {
            val movies = adapterNet.toDomain(response.data!!.docs
                .filter { it.name.isNotEmpty() }
                .toList()
            )
            //Log.d("mytag", "size: ${movies.size} data: \n ${movies.joinToString("\n")}");
            return MoviesData(setFavorites(movies, favorites))
        } else if (response is NetworkResponse.Error) {
            return MoviesData(errorMessage = response.errorMsg, movies = emptyList())
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
        movie.isFavorite = newState
        if (newState) {
            movieDao.insert(adapterDb.fromDomain(movie))
        } else {
            movieDao.delete((adapterDb.fromDomain(movie)))
        }
    }

    suspend fun searchMovie(query: String): MoviesData {
        val favorites = adapterDb.toDomain(movieDao.getMovies())
        if (mock) {
            //Log.d("mytag", "search for \"${query}\"...")
            delay(1000)
            val movies = MockEntities.mockMovies().take(5).map {
                it.copy(title = "${query} ${it.id}")
            }
            val response = MovieResponse(
                docs = adapterNet.fromDomain(movies).toTypedArray()
            )
            //Log.d("mytag", "search for \"${query}\"...DONE")
            return toMoviesData(NetworkResponse.Success(response), favorites)
        } else {
            val response = requestNetwork<MovieResponse> {
                api.searchMovie(query = query)
            }
            return toMoviesData(response, favorites)
        }
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsData {
        val response = requestNetwork<MovieDetailsResponse> {
            api.getMovie(id)
        }
        if (response is NetworkResponse.Success) {
            val movie = adapterNet.toDomain(response.data!!)
            return MovieDetailsData(
                movie = movie
            )
        } else if (response is NetworkResponse.Error) {
            return MovieDetailsData(errorMessage = response.errorMsg)
        } else {
            return MovieDetailsData()
        }
    }
}

