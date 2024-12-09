package com.pablok.kinopoisklight.ui

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pablok.kinopoisklight.ui.navigation.NavBar
import com.pablok.kinopoisklight.ui.navigation.NavDestination
import com.pablok.kinopoisklight.ui.navigation.NavGraph
import com.pablok.kinopoisklight.ui.theme.KinopoiskLightTheme

@Composable
fun AppScreen(
    startDestination: String
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current

    BackHandler {
        (context as ComponentActivity).finishAffinity()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        content = { contentPadding ->
            Content(
                contentPadding = contentPadding,
                navController = navController,
                startDestination = startDestination
            )
        },
        bottomBar = {
            NavBar(
                navController = navController,
                navBackStackEntry = navBackStackEntry,
            )
        }
    )
}

@Composable
fun Content(
    contentPadding: PaddingValues,
    navController: NavHostController,
    startDestination: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        NavGraph(
            navController = navController,
            startDestination = startDestination
        )
    }
}

@Preview(name = "Main light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Main in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreview() {
    KinopoiskLightTheme() {
        AppScreen(
            startDestination = NavDestination.Search.route
        )
    }
}

