package com.pablok.kinopoisklight.actors_repository

import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.ActorDetails
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.database.internal.DatabaseDataAdapter
import com.pablok.kinopoisklight.database.internal.dao.MovieDao
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.NetworkResponse
import com.pablok.kinopoisklight.network.dto.ActorDetailsNet
import com.pablok.kinopoisklight.network.internal.NetworkDataAdapter
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

data class ActorData(
    val actor: ActorDetails? = null,
    val errorMessage: String? = null,
)

class ActorsRepository @Inject constructor(
    private val api: KinopoiskApi,
    private val movieDao: MovieDao,
    private val adapterNet: NetworkDataAdapter,
    private val adapterDb: DatabaseDataAdapter
) {
    private val mock = false

    suspend fun getActor(id: Int): ActorData {
        val favorites = adapterDb.toDomain(movieDao.getMovies())
        if (mock) {
            delay(200)
            val response = adapterNet.fromDomain(MockEntities.mockActorDetails())
            return toActorData(NetworkResponse.Success(response), favorites)
        } else {
            val response = requestNetwork<ActorDetailsNet> {
                api.getActor(id)
            }
            return toActorData(response, favorites)
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

    private fun toActorData(
        response: NetworkResponse<ActorDetailsNet>,
        favorites: List<Movie>
    ): ActorData {
        if (response is NetworkResponse.Success) {
            return ActorData(adapterNet.toDomain(response.data!!))
        } else if (response is NetworkResponse.Error) {
            return ActorData(errorMessage = response.errorMsg, actor = null)
        }
        return ActorData()
    }
}