package com.pablok.kinopoisklight.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.elements.FavoriteIcon
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
