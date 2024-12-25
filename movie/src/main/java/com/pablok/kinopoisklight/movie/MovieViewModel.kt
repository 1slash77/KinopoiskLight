package com.pablok.kinopoisklight.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pablok.kinopoisklight.MoviesRepository
import com.pablok.kinopoisklight.core.dto.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MovieScreenState(
    val movies: List<Movie>? = null,
    val isRefreshing: Boolean = true,
    val errorMessage: String? = null,
    val showOnlyFavorites: Boolean = false,

    val searchText: String = "",

    )

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val _screenState = mutableStateOf(MovieScreenState())
    val screenState: State<MovieScreenState> get() = _screenState
}