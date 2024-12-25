package com.pablok.kinopoisklight.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.components.ErrorContent
import com.pablok.kinopoisklight.ui.components.MyTextField
import com.pablok.kinopoisklight.ui.elements.TopAppBarFavorites
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    title: String,
    viewModel: SearchViewModel = hiltViewModel(),
    onShowMovie: (Int) -> Unit,
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

    Content(
        title = "Фильмы",
        showOnlyFavorites = state.showOnlyFavorites,
        onShowFavoritesChanged = { viewModel.showOnlyFavorites(!state.showOnlyFavorites) },
        searchText = state.searchText,
        onSearchTextChanged = { viewModel.onSearchTextChanged(it) },
        onSearchClicked = { viewModel.onSearchClicked(state.searchText) },
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        movies = state.movies,
        errorMessage = state.errorMessage,
        onFavoriteChanged = { movie, checked ->
            viewModel.onFavoriteChanged(movie, checked)
        },
        onShowMovie = onShowMovie
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    title: String,
    movies: List<Movie>?,
    errorMessage: String?,
    showOnlyFavorites: Boolean,
    onShowFavoritesChanged: (Boolean) -> Unit,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onFavoriteChanged: (Movie, Boolean) -> Unit,
    onShowMovie: (Int) -> Unit
) {
    val padding = 8.dp
    Scaffold(
        topBar = {
            TopAppBarFavorites(
                title = title,
                showOnlyFavorites = showOnlyFavorites,
                onClickFavorites = { onShowFavoritesChanged(!showOnlyFavorites) }
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(padding)
            //.background(Color.Blue.copy(alpha = 0.3f))
        ) {
            SearchField(
                title = "Поиск фильмов",
                text = searchText,
                onTextChanged = onSearchTextChanged,
                onSearchClicked = onSearchClicked
            )

            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxWidth()
                    //.background(Color.Red)
                    .padding(padding)
                ,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            ) {
                if (!isRefreshing) {
                    if (errorMessage != null) {
                        ErrorContent(errorMessage, onClick = onRefresh)
                    } else if (movies != null) {
                        MoviesContent(movies, onFavoriteChanged = onFavoriteChanged,
                            onMovieClick = onShowMovie
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesContent(
    movies: List<Movie>,
    onFavoriteChanged: (Movie, Boolean) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.Green)
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) {
                MovieItem(
                    movie = it,
                    isFavorite = it.isFavorite,
                    onFavoriteChanged = { checked ->
                        onFavoriteChanged(it, checked)
                    },
                    onClick =  { onMovieClick(it.id) }
                )
            }
        }
}

@Composable
fun SearchField(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
) {
    MyTextField(
        title, text,
        onTextChanged = onTextChanged,
        onSearchClicked = onSearchClicked
    )
}

@Preview
@Composable
fun ContentPreview() {
    KinopoiskLightTheme {
        Content(
            title = "Фильмы",
            movies = MockEntities.mockMovies(),
            errorMessage = null,
            showOnlyFavorites = false,
            onShowFavoritesChanged = {},
            searchText = "",
            onSearchTextChanged = {},
            onSearchClicked = {},
            isRefreshing = false,
            onRefresh ={},
            onFavoriteChanged = {it1, it2 ->},
            onShowMovie = {}
        )
    }
}
