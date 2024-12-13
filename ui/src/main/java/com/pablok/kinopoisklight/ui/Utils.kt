package com.pablok.kinopoisklight.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun debugPlaceholder(@DrawableRes res: Int = com.pablok.kinopoisklight.ui.R.drawable.async_image_placeholder) =
    if (LocalInspectionMode.current) {
        painterResource(res)
    } else {
        null
    }