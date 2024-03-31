package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(navController: NavController) {
  WatchlistAppScaffold(navController = navController)
}


@Composable
fun WatchlistAppScaffold(navController: NavController) {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = { SimpleTopAppBar(navController,"Watchlist", false) },
            bottomBar = { SimpleBottomAppBar(navController) }
        ) { innerPadding ->
            MovieWatchList(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}



@Composable
fun MovieWatchList(navController: NavController, modifier: Modifier = Modifier) {
    val movies = getMovies().subList(0,4)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp) // Adds space between items
    ) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate("detailscreen/$movieId")
                },
                navController = navController
            )
        }
    }
}
