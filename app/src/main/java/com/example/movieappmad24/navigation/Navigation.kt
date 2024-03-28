package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.AppScaffold
import com.example.movieappmad24.screens.DetailScreen
import java.lang.reflect.Modifier

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController() // create a NavController instance
    NavHost(
        navController = navController, // pass the NavController to NavHost
        startDestination = "homescreen"
    ) { // pass a start destination
        composable(route = "homescreen") {
            AppScaffold(navController = navController)
        }
        composable(
            route = "detailscreen/{movieId}",
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let { movieId ->
                DetailScreen(navController = navController, movieId = movieId)
            }
        }
    }
}
