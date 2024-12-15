package com.pablok.kinopoisklight.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel
import com.pablok.kinopoisklight.core.MockEntitis
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.network.KinopoiskApi
import kotlinx.coroutines.delay

data class SearchScreenState(
    val movies: List<Movie>? = null,
    val isRefreshing: Boolean = true,


    val total: Int? = null,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: KinopoiskApi
/*    private val comicsRepository: ComicsRepository,
    appSettings: AppSettings*/
) : ViewModel() {
    private val _screenState = mutableStateOf(SearchScreenState())
    val screenState: State<SearchScreenState> get() = _screenState

    suspend fun fetch() {
        _screenState.value = screenState.value.copy(isRefreshing = true)
        //delay(700)
        val res = api.getMovie(258687)
        Log.d("mytag", "res: $res")
        _screenState.value = screenState.value.copy(movies = MockEntitis.mockMovies())
        _screenState.value = screenState.value.copy(isRefreshing = false)
    }
}
