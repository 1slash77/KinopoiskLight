package com.pablok.kinopoisklight.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

data class NavItem(
    val destination: NavDestination,
    val icon: ImageVector,
    val iconSelected: ImageVector,
)

private val navItems = listOf(
    NavItem(
        destination = NavDestination.Search,
        icon = Icons.Outlined.Search,
        iconSelected = Icons.Filled.Search
    ),
    NavItem(
        destination = NavDestination.Movie,
        icon = Icons.Outlined.PlayArrow,
        iconSelected = Icons.Filled.PlayArrow
    ),
    NavItem(
        destination = NavDestination.Actor,
        icon = Icons.Outlined.Person,
        iconSelected = Icons.Filled.Person
    )
)

@Composable
fun NavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.destination.route,
                onClick = {
                    goToScreen(
                        navController,
                        currentDestination?.route,
                        item.destination.route
                    )
                },
                label = {
                    Text(
                        text = item.destination.title,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.route == item.destination.route) item.iconSelected else item.icon,
                        contentDescription = item.destination.title
                    )
                }
            )
        }
    }
}

private fun goToScreen(
    navController: NavHostController,
    currentRoute: String?,
    selectRoute: String
) {
    if (currentRoute != selectRoute) navController.navigate(selectRoute) { popUpTo(0) }
}