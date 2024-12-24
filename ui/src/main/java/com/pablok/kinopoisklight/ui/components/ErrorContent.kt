package com.pablok.kinopoisklight.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorContent(
    errorMessage: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text("Попробуйте еще раз" )
        errorMessage?.let {
            Text("Ошибка $it")
        }
        FilledTonalButton(onClick = onClick) {
            Text("Обновить")
        }
    }
}