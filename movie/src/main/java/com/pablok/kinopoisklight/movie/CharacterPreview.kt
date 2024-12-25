package com.pablok.kinopoisklight.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pablok.kinopoisklight.core.MockEntities
import com.pablok.kinopoisklight.core.dto.Actor
import com.pablok.kinopoisklight.ui.debugPlaceholder
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun CharacterItem(
    character: Actor
) {
    Row(modifier = Modifier
        .fillMaxWidth()

    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp, 70.dp)
            //.height(200.dp)
            /*                    .clip(
                                    MaterialTheme.shapes.medium.copy(
                                        bottomEnd = CornerSize(0.dp),
                                        bottomStart = CornerSize(0.dp)
                                    )
                                )*/
            ,
            model = character.photoUrl,
            placeholder = debugPlaceholder(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(character.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(character.character,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
fun CharacterItemPreview() {
    KinopoiskLightTheme {
        CharacterItem(MockEntities.mockActor())
    }
}