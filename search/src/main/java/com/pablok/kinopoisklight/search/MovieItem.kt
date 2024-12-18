package com.pablok.kinopoisklight.search

import android.util.Log
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
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntitis
import com.pablok.kinopoisklight.core.dto.Movie
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.elements.FavoriteIcon
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun MovieItem(
    movie: Movie,
    isFavorite: Boolean,
    onAddFavoriteComic: (Movie) -> Unit,
    onDeleteFavoriteComic: (Movie) -> Unit,
) {
    //TODO change UI for MovieItem
    val savedToFavorites by rememberUpdatedState(isFavorite)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Box() {
            Log.d("mytag", "AsyncImage: ${movie.thumbnail.path}");
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
                //model = "${movie.thumbnail.path}.${movie.thumbnail.extension}",
                model = movie.thumbnail.path,
                placeholder = debugPlaceholder(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            FavoriteIcon(
                selected = savedToFavorites,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                if (isFavorite) onDeleteFavoriteComic(movie)
                else onAddFavoriteComic(movie)
            }
        }

        /*        HorizontalDivider(
                    thickness = 4.dp,
                    color = MaterialTheme.colorScheme.errorContainer
                )*/

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                //.padding(horizontal = 3.dp)
            ,
            text = movie.title,
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
