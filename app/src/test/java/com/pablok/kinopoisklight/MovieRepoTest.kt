package com.pablok.kinopoisklight


import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.database.dto.MovieLocal
import com.pablok.kinopoisklight.database.internal.DatabaseDataAdapter
import com.pablok.kinopoisklight.database.internal.dao.MovieDao
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.dto.MovieNet
import com.pablok.kinopoisklight.network.dto.MovieResponse
import com.pablok.kinopoisklight.network.internal.NetworkDataAdapter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class MoviesRepositoryTest {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var adapterLocal: DatabaseDataAdapter
    private lateinit var adapterNet: NetworkDataAdapter
    private lateinit var api: KinopoiskApi
    private lateinit var movieDao: MovieDao

    private val offset = 0
    private val limit = 20

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        api = mock()
        movieDao = mock()
        adapterLocal = mock()
        adapterNet = mock()
        moviesRepository = MoviesRepository(
            api,
            movieDao,
            adapterNet,
            adapterLocal
        )
    }

    private fun prepareMockMovie(): Movie = MockEntities.mockMovie()

    private fun prepareMockLocalMovie(
        movie: Movie = prepareMockMovie()
    ): MovieLocal = DatabaseDataAdapter().fromDomain(movie)

    private fun prepareMockNetworkMovie(
        movie: Movie = prepareMockMovie()
    ): MovieNet = NetworkDataAdapter().fromDomain(movie)

    @Test
    fun `add movie to favorites`() = runTest {
        // Arrange
        val movie = prepareMockMovie()
        val localMovie = prepareMockLocalMovie()

        whenever(adapterLocal.fromDomain(movie)).thenReturn(localMovie)

        // Act
        moviesRepository.updateFavoriteState(movie, true)

        // Assert
        verify(movieDao).insert(localMovie)
    }

    @Test
    fun `remove movie from favorites`() = runTest {
        val movie = prepareMockMovie()
        val localMovie = prepareMockLocalMovie()

        whenever(adapterLocal.fromDomain(movie)).thenReturn(localMovie)

        // Act
        moviesRepository.updateFavoriteState(movie, false)

        // Assert
        verify(movieDao).delete(localMovie)
    }

    @Test
    fun `get favorite movies`() = runBlocking {
        // Arrange
        val localMovies = listOf(prepareMockLocalMovie())

        whenever(movieDao.getMovies()).thenReturn(localMovies)

        // Act
        val result = moviesRepository.getFavorites()

        // Assert
        assertNotNull(result)
    }

    @Test
    fun `get recent movies, returns 1 movie`() =
        runBlocking {
            val movies = listOf(prepareMockMovie())
            val localMovies = adapterLocal.fromDomain(movies)
            val networkMovies = adapterNet.fromDomain(movies)
            val response = Response.success(MovieResponse(networkMovies.toTypedArray()))

            whenever(movieDao.getMovies()).thenReturn(localMovies)

            whenever(api.getRecentMovies()).thenReturn(response)

            whenever(adapterNet.toDomain(networkMovies))
                .thenReturn(movies)

            // Act
            val result = moviesRepository.getRecentMovie()

            // Assert
            assertNotNull(result)
            assertEquals(movies, result.movies!!)
            assertNull(result.errorMessage)
        }

    @Test
    fun `get recent movies, returns an error`() =
        runBlocking {
            whenever(movieDao.getMovies()).thenReturn(emptyList())
            val errorJson = """{"message": "Bad Request", "code": 400}"""
            val errorResponse = Response.error<MovieResponse>(
                400,
                errorJson.toResponseBody("application/json".toMediaTypeOrNull())
            )
            whenever(api.getRecentMovies()).thenReturn(errorResponse)

            // Act
            val result = moviesRepository.getRecentMovie()

            // Assert
            assertNotNull(result)
            assertNotNull(result.errorMessage)
            assertNull(result.movies)
        }

}