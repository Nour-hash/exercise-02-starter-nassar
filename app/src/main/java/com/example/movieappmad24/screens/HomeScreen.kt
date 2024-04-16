package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewModel.MoviesViewModel
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(navController: NavController,moviesViewModel: MoviesViewModel) {
    AppScaffold(navController = navController, moviesViewModel = moviesViewModel)
}


@Composable
fun AppScaffold(navController: NavController,moviesViewModel: MoviesViewModel) {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = { SimpleTopAppBar(navController = navController, title = "MovieApp", backButton = false) },
            bottomBar = { SimpleBottomAppBar(navController) }
        ) { innerPadding ->
            MovieList(navController = navController, modifier = Modifier.padding(innerPadding), moviesViewModel = moviesViewModel)
        }
    }
}

@Composable
fun MovieList(navController: NavController, modifier: Modifier = Modifier,moviesViewModel: MoviesViewModel) {

    val movies = moviesViewModel.movieList

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
                onFavoriteClick = {
                    moviesViewModel.toggleIsFavouriteState(movie = movie)
                }
            )
        }
    }
}



@Preview
@Composable
fun MovieAppPreview() {
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()
    AppScaffold(navController = navController, moviesViewModel = moviesViewModel)
}
