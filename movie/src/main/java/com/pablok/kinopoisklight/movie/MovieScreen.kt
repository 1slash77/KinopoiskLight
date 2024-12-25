package com.pablok.kinopoisklight.movie

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.MovieDetails
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.elements.TopAppBarFavorites
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieScreen(
    title: String,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val state by viewModel.screenState
    MovieDetails(MockEntities.mockMovieDetails())
}

@Composable
fun MovieDetails(
    movie: MovieDetails,
    showOnlyFavorites: Boolean = false,

    onShowFavoritesChanged: (Boolean) -> Unit = {},
) {
    val padding = 8.dp
    Scaffold(
        topBar = {
            TopAppBarFavorites(
                title = movie.name,
                showOnlyFavorites = showOnlyFavorites,
                onClickFavorites = { onShowFavoritesChanged(!showOnlyFavorites) }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(padding)
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

}

@Preview
@Composable
fun MovieDetailsPreview() {
    KinopoiskLightTheme {
        MovieDetails(MockEntities.mockMovieDetails(),
        )
    }
}