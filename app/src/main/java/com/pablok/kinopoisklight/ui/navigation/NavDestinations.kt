package com.pablok.kinopoisklight.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablok.kinopoisklight.movie.MovieScreen
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
        route = "movie/{uId}",
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
        composable(NavDestination.Search.route) {
            SearchScreen(NavDestination.Search.title) { movieId ->
                Log.d("mytag", "click: $movieId")
                navController.navigate(
                    NavDestination.Movie.route //Just modify your route accordingly
                        .replace(
                            oldValue = "{uId}",
                            newValue = movieId.toString()
                        )
                )
            }
        }

        composable(NavDestination.Movie.route) { navBackStackEntry ->
            val uId = navBackStackEntry.arguments?.getString("uId")
            Log.d("mytag", "uId: $uId")
            MovieScreen(uId)
        }

        composable(NavDestination.Actor.route) {
            TmpActorScreen(NavDestination.Actor.title)
        }
    }
}

@Composable
fun TmpActorScreen(
    title: String
) {
    Text(title)
}