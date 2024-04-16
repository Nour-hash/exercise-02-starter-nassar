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
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewModel.MoviesViewModel
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(navController: NavController, moviesViewModel: MoviesViewModel) {
    WatchlistAppScaffold(navController = navController, moviesViewModel = moviesViewModel)
}



@Composable
fun WatchlistAppScaffold(navController: NavController, moviesViewModel: MoviesViewModel) {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = { SimpleTopAppBar(navController,"Watchlist", false) },
            bottomBar = { SimpleBottomAppBar(navController) }
        ) { innerPadding ->
            MovieWatchList(navController = navController, moviesViewModel = moviesViewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}



@Composable
fun MovieWatchList(navController: NavController, moviesViewModel: MoviesViewModel, modifier: Modifier = Modifier) {
    val movies = moviesViewModel.movieList.filter { movie: Movie -> movie.isFavorite }

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
                onFavoriteClick = { moviesViewModel.toggleIsFavouriteState(movie) }
            )
        }
    }
}

