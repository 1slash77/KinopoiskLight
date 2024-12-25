package com.pablok.kinopoisklight.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pablok.kinopoisklight.ui.R
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun FavoriteIconBase(
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            //.background(Color.Green)
        ,
        onClick = onClick
    ) {
        Icon(
            imageVector = if (selected) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = null,
            modifier = Modifier
                .clip(
                    MaterialTheme.shapes.medium)
                .background(Color.White.copy(alpha = 0.3f))
            ,
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun FavoriteIcon(
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
/*    Box(
        modifier = modifier
    ) {*/
        FavoriteIconBase(
            selected = selected,
            modifier = modifier,
            onClick = onClick
        )
    //}
}

@Preview
@Composable
fun FavoriteIconPreview() {
    KinopoiskLightTheme {
/*        Box(Modifier
            .size(100.dp, 100.dp)
            .background(MaterialTheme.colorScheme.tertiary)

        )*/
        Box() {
            Image(
                painterResource(R.drawable.async_image_placeholder), contentDescription = "",
                //modifier = Modifier.size(200.dp, 400.dp)
            )
            FavoriteIcon(
                selected = false,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {}
        }
    }
}
