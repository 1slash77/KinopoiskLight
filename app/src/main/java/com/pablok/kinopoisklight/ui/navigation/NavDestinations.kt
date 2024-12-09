package com.pablok.kinopoisklight.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class NavDestination(
    val route: String,
    val title: String,
) {
    data object Search: NavDestination(
        route = "search",
        title = "Search"
    )

    data object Movie: NavDestination(
        route = "movie",
        title = "Movie"
    )

    data object Actor: NavDestination(
        route = "actor",
        title = "Actor"
    )
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        //TODO add animation
        //composableWithAnimations(MainScreenContent.Characters.route) {
        composable(NavDestination.Search.route) {
            TmpSearchScreen(NavDestination.Search.title)
        }

        composable(NavDestination.Movie.route) {
            TmpSearchScreen(NavDestination.Movie.title)
        }

        composable(NavDestination.Actor.route) {
            TmpSearchScreen(NavDestination.Actor.title)
        }
    }
}

@Composable
fun TmpSearchScreen(
    title: String
) {
    Text(title)
}

@Composable
fun TmpMovieScreen(
    title: String
) {
    Text(title)
}

@Composable
fun TmpActorScreen(
    title: String
) {
    Text(title)
}