package com.pablok.kinopoisklight.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.elements.TopAppBarFavorites
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    title: String,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val state by viewModel.screenState

    val onRefresh: () -> Unit = {
            viewModel.fetch()
    }

    if (state.movies == null) {
        LaunchedEffect(Unit) {
            onRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBarFavorites(
                title = title,
                showOnlyFavorites = state.showOnlyFavorites,
                onClickFavorites = { viewModel.showOnlyFavorites(!state.showOnlyFavorites) }
            )
        }
    ) {
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
        ) {
            if (!state.isRefreshing) {
                val movies = state.movies
                val error = state.errorMessage
                if (movies == null) {
                    if (error != null) {
                        ErrorContent(error, onClick = onRefresh)
                    }
                } else {
                    Content(movies, false) { movie, checked ->
                        viewModel.onFavoriteChanged(movie, checked)
                    }
                }
            }
        }
    }
}

//TODO move to :ui
@Composable
fun ErrorContent(
    errorMessage: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text("Попробуйте еще раз" )
        errorMessage?.let {
            Text("Ошибка $it")
        }
        FilledTonalButton(onClick = onClick) {
            Text("Обновить")
        }
    }
}




@Composable
fun Content(
    movies: List<Movie>,
    showOnlyFavorites: Boolean,
    onFavoriteChanged: (Movie, Boolean) -> Unit

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth()
        ) {
            items(movies) {
                MovieItem(
                    movie = it,
                    isFavorite = it.isFavorite,
                    onFavoriteChanged = { checked ->
                        onFavoriteChanged(it, checked)
                    },
                )
            }
        }


}

@Preview
@Composable
fun ContentPreview() {
    KinopoiskLightTheme {
        Content(
            movies = MockEntities.mockMovies(),
            showOnlyFavorites = false,
            onFavoriteChanged = {it1 ,it2 -> }
        )
    }

}
