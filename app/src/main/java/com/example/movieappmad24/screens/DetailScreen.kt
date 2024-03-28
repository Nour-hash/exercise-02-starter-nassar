package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(navController: NavController, movieId: String) {
    // Fetch movie details based on movieId if necessary

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Movie ID: $movieId") // Show movie ID

    }
}
