package com.pablok.kinopoisklight.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun debugPlaceholder(@DrawableRes res: Int = R.drawable.async_image_placeholder) =
    if (LocalInspectionMode.current) {
        painterResource(res)
    } else {
        null
    }

@Composable
fun favoriteIcon(
    selected: Boolean = false
) = if (selected) Icons.Default.Star else Icons.Default.StarOutline