package com.pablok.kinopoisklight.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablok.kinopoisklight.search.SearchScreen

sealed class NavDestination(
    val route: String,
    val title: String,
) {
    data object Search: NavDestination(
        route = "search",
        title = "Поиск фильмов"
    )

    data object Movie: NavDestination(
        route = "movie",
        title = "Фильм"
    )

    data object Actor: NavDestination(
        route = "actor",
        title = "Актер"
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
            SearchScreen(NavDestination.Search.title)
        }

        composable(NavDestination.Movie.route) {
            TmpMovieScreen(NavDestination.Movie.title)
        }

        composable(NavDestination.Actor.route) {
            TmpActorScreen(NavDestination.Actor.title)
        }
    }
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