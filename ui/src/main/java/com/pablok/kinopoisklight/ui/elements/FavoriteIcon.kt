package com.pablok.kinopoisklight.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablok.kinopoisklight.ui.R
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import kotlinx.coroutines.FlowPreview

@Composable
fun FavoriteIconBase(
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.5f))
        ,
        onClick = onClick
    ) {
        Icon(
            imageVector = if (selected) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = null,
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
    Box(
        modifier = modifier
            //.background(Color.White.copy(alpha = 1f))
            //.padding(2.dp)
    ) {
        FavoriteIconBase(
            selected = selected,
            modifier = modifier,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun FavoriteIconPreview() {
    KinopoiskLightTheme {
        Box(Modifier
            .size(100.dp, 100.dp)
            .background(MaterialTheme.colorScheme.tertiary)

        )
/*        Box() {
            Image(
                painterResource(R.drawable.async_image_placeholder), contentDescription = "",
                //modifier = Modifier.size(200.dp, 400.dp)
            )
            FavoriteIcon(
                selected = false,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {}
        }*/
    }
}
