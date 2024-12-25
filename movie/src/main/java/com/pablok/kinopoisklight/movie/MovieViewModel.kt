package com.pablok.kinopoisklight.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablok.kinopoisklight.MoviesRepository
import com.pablok.kinopoisklight.core.dto.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailsScreenState(
    val details: MovieDetails? = null,
    val isRefreshing: Boolean = true,
    val errorMessage: String? = null,


    )

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val _screenState = mutableStateOf(MovieDetailsScreenState())
    val screenState: State<MovieDetailsScreenState> get() = _screenState

    fun fetch(movieId: String?) {
        _screenState.value = screenState.value.copy(isRefreshing = true)
        var id = 258687
        try {
            id = Integer.valueOf(movieId!!)
        }catch (_: Exception) {}

        viewModelScope.launch {
            val movie = movieRepo.getMovieDetails(id)
            _screenState.value = screenState.value.copy(
                details = movie.movie,
                errorMessage = movie.errorMessage,
                isRefreshing = false
            )
        }
    }
}