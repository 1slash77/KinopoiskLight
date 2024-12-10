package com.pablok.kinopoisklight.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SearchScreen(
    title: String,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Text(title)
}