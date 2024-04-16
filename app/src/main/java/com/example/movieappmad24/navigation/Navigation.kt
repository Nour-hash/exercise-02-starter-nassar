package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewModel.MoviesViewModel

@Composable
fun Navigation(movies: List<Movie>) {
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController , moviesViewModel = moviesViewModel)
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = "movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let { id ->
                val movie = movies.find { it.id == id }
                movie?.let {
                    DetailScreen(navController = navController, movieId = id,  moviesViewModel = moviesViewModel)
                }
            }
        }
        composable(Screen.WatchlistScreen.route)
        {
            WatchlistScreen( navController = navController, moviesViewModel = moviesViewModel)
        }
    }
}
