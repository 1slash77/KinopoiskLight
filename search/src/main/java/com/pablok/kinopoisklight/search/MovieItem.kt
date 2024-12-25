package com.pablok.kinopoisklight.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.elements.GridItem
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme


@Composable
fun MovieItem(
    movie: Movie,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteChanged: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    GridItem(
        title = movie.title,
        imageModel = movie.thumbnail.path,
        isFavorite = isFavorite,
        onFavoriteChanged = onFavoriteChanged,
        onClick = onClick
    )
}


@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    KinopoiskLightTheme {
        MovieItem(
            movie = MockEntities.mockMovie(),
            isFavorite = false,
            onFavoriteChanged = {},
            onClick = {}
        )
    }
}
