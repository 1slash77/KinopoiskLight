package com.pablok.kinopoisklight.ui.elements

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun MyCard(
    isSelected: Boolean,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        content()
    }
}

@Composable
fun GridItem(
    title: String,
    imageModel: Any?,
    isFavorite: Boolean,
    onFavoriteChanged: (Boolean) -> Unit
) {
    val savedToFavorites by rememberUpdatedState(isFavorite)
    MyCard(isSelected = false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
            /*            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )*/
        ) {
            Box() {
                Log.d("mytag", "\"${title}\"    imageModel: $imageModel")
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
                    model = imageModel,
                    placeholder = debugPlaceholder(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )

                FavoriteIcon(
                    selected = savedToFavorites,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    onFavoriteChanged(!isFavorite)
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                text = title,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun MyCardPreview() {

    KinopoiskLightTheme {
        GridItem(
            title = "Title",
            imageModel = null,
            isFavorite = false,
            onFavoriteChanged = {}
        )
    }
}