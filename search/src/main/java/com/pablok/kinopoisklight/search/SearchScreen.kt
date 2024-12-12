package com.pablok.kinopoisklight.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntitis
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme


@Composable
fun SearchScreen(
    title: String,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Text(title)
    MovieItem (
        movie = MockEntitis.mockMovie(),
        isFavorite = true,
        onAddFavoriteComic = {},
        onDeleteFavoriteComic = {}
    )
}


@Composable
private fun MovieItem(
    movie: Movie,
    isFavorite: Boolean,
    onAddFavoriteComic: (Movie) -> Unit,
    onDeleteFavoriteComic: (Movie) -> Unit,
) {
    val savedToFavorites by rememberUpdatedState(isFavorite)
    val iconFavorite = if (savedToFavorites) Icons.Default.Star else Icons.Default.StarOutline

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Box() {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        MaterialTheme.shapes.medium.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    ),
                model = "${movie.thumbnail.path}.${movie.thumbnail.extension}",
                placeholder = debugPlaceholder(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            FavoriteIcon(
                selected = savedToFavorites,
                modifier = Modifier.align(Alignment.TopEnd)
            ) { selected ->
                if (selected) onAddFavoriteComic(movie)
                else onDeleteFavoriteComic(movie)
            }
        }

/*        HorizontalDivider(
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.errorContainer
        )*/

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .padding(horizontal = 3.dp),
            text = movie.title.uppercase(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

/*        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val newFavoriteState = !savedToFavorites
                if (newFavoriteState) onAddFavoriteComic(movie)
                else onDeleteFavoriteComic(movie)
            }
        ) {
            Icon(
                imageVector = iconFavorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        }*/



    }
}

@Composable
fun FavoriteIcon(
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick(!selected) }
    ) {
        Icon(
            imageVector = if (selected) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
    }


}


@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    KinopoiskLightTheme {
       MovieItem (
            movie = MockEntitis.mockMovie(),
            isFavorite = false,
            onAddFavoriteComic = {},
            onDeleteFavoriteComic = {}
        )
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int = 0) =
    if (LocalInspectionMode.current) {
        painterResource(id = com.pablok.kinopoisklight.ui.R.drawable.async_image_placeholder)
    } else {
        null
    }