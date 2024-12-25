package com.pablok.kinopoisklight.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.MovieDetails
import com.pablok.kinopoisklight.ui.components.ErrorContent
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.elements.TopAppBarFavorites
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieScreen(
    movieId: String?,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val state by viewModel.screenState

    val onRefresh: () -> Unit = {
        viewModel.fetch(movieId)
    }

    if (state.details == null) {
        LaunchedEffect(Unit) {
            onRefresh()
        }
    }

    MovieDetails(state.details,
        isRefreshing = state.isRefreshing,
        onFavoriteChanged = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetails(
    movie: MovieDetails?,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,

    onFavoriteChanged: (Boolean) -> Unit = {},
) {
    val isFavorite = movie?.isFavorite ?: false
    val padding = 8.dp
    Scaffold(
        topBar = {
            TopAppBarFavorites(
                title = movie?.name ?: "",
                showOnlyFavorites = isFavorite,
                onClickFavorites = { onFavoriteChanged(!isFavorite) }
            )
        }
    ) { contentPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Red)
                .padding(contentPadding),
            isRefreshing = isRefreshing,
            onRefresh = { },
        ) {
            if (!isRefreshing) {
                if (errorMessage != null) {
                    ErrorContent(errorMessage, onClick = {})
                } else if (movie != null) {
                    MovieContent(movie)
                }
            }
        }
    }

}

@Composable
fun MovieContent(
    movie: MovieDetails,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
            /*                    .clip(
                                    MaterialTheme.shapes.medium.copy(
                                        bottomEnd = CornerSize(0.dp),
                                        bottomStart = CornerSize(0.dp)
                                    )
                                )*/
            ,
            model = movie.posterUrl,
            placeholder = debugPlaceholder(),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(16.dp))
        Text("Год: ${movie.year}",
            style = MaterialTheme.typography.bodyLarge

        )
        Text("Продолжительность: ${movie.movieLength} мин.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        Text("${movie.description}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(16.dp))
        Text("Актеры:",
            style = MaterialTheme.typography.bodyLarge
        )

        for (character in movie.persons) {
            Spacer(Modifier.height(16.dp))
            CharacterItem(character)
        }
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    KinopoiskLightTheme {
        MovieDetails(MockEntities.mockMovieDetails(),
        )
    }
}