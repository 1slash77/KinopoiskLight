package com.pablok.kinopoisklight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablok.kinopoisklight.ui.AppScreen
import com.pablok.kinopoisklight.ui.navigation.NavDestination
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme
import dagger.hilt.android.AndroidEntryPoint

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

