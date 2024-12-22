package com.pablok.kinopoisklight.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.pablok.kinopoisklight.MoviesRepository
import com.pablok.kinopoisklight.core.dto.Movie

import kotlinx.coroutines.launch

data class SearchScreenState(
    val movies: List<Movie>? = null,
    val isRefreshing: Boolean = true,
    val errorMessage: String? = null,
    val showOnlyFavorites: Boolean = false

)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val _screenState = mutableStateOf(SearchScreenState())
    val screenState: State<SearchScreenState> get() = _screenState

    fun fetch() {
        _screenState.value = screenState.value.copy(isRefreshing = true)
        viewModelScope.launch {
            //val res = movieRepo.getMovie(258687)
            val res = movieRepo.getRecentMovie()
            //Log.d("mytag", "res: $res")
            _screenState.value = screenState.value.copy(
                movies = res.movies,
                errorMessage = res.errorMessage
            )
            _screenState.value = screenState.value.copy(isRefreshing = false)
        }
    }

    fun showOnlyFavorites(show: Boolean) {
        _screenState.value = screenState.value.copy(showOnlyFavorites = show)
        if (show) {
            fetchFavorites()
        } else {
            fetch()
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            _screenState.value = screenState.value.copy(movies = movieRepo.getFavorites())
        }
    }

    fun onFavoriteChanged(movie: Movie, newState: Boolean) {
        _screenState.value = screenState.value.copy(
            movies = screenState.value.movies!!.map {
                if (it.id == movie.id) it.copy(isFavorite = true) else it
            }
        )

        viewModelScope.launch {
            movieRepo.updateFavoriteState(movie, newState)
            if (screenState.value.showOnlyFavorites) {
                _screenState.value = screenState.value.copy(movies = movieRepo.getFavorites())
            }
        }
    }
}
