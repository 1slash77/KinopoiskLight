package com.pablok.kinopoisklight

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pablok.kinopoisklight.ui.AppScreen
import com.pablok.kinopoisklight.ui.navigation.NavDestination
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import dagger.hilt.android.AndroidEntryPoint

//import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            KinopoiskLightTheme {
                AppScreen(NavDestination.Search.route)
            }
        }
    }
}

