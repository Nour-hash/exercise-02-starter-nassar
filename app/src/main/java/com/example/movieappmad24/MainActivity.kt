package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScaffold()
                }
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie, isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Director: ${movie.director}")
            Text(text = "Release Year: ${movie.year}")
            Text(text = "Genre: ${movie.genre}")
            Text(text = "Actors: ${movie.actors}")
            Text(text = "Rating: ${movie.rating}")
            Text(text = "_______________________________________________")
            Text(text = "Plot: ${movie.plot}")
            // Add more details as needed
        }
    }
}

@Composable
fun MovieRow(movie: Movie) {
    var isFavorite by remember { mutableStateOf(false) }
    var showArrow by remember { mutableStateOf(false) }

    MovieAppMAD24Theme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                MovieCardHeader(movie, isFavorite, onFavoriteClick = { isFavorite = !isFavorite })
                MovieTitleBar(movie, showArrow, onArrowClick = { showArrow = !showArrow })
                MovieDetails(movie, showArrow)
            }
        }
    }
}

@Composable
fun MovieCardHeader(movie: Movie, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.align(Alignment.CenterStart),
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp), // Rounded corners at the top
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = movie.images.firstOrNull() ?: "",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) // Ensure the image also has rounded corners

            )
        }
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = { onFavoriteClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                tint = Color.Red,
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}

@Composable
fun MovieTitleBar(movie: Movie, showArrow: Boolean, onArrowClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)) // Apply rounded corners to the bottom
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.title,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            color = Color.Black
        )
        IconButton(onClick = { onArrowClick() }) {
            Icon(
                imageVector = if (showArrow) Icons.Filled.KeyboardArrowUp else Icons.Default.KeyboardArrowUp,
                contentDescription = "Expand",
                modifier = Modifier.graphicsLayer(rotationZ = if (showArrow) 180f else 0f)
            )
        }
    }
}


@Composable
fun AppScaffold() {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = { AppTopBar() },
            bottomBar = { AppBottomBar() }
        ) { innerPadding ->
            MovieList(Modifier.padding(innerPadding))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "MovieApp",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        },
        actions = {}, // You can add actions here if needed
        navigationIcon = {} // Add a navigation icon if necessary
    )
}


@Composable
fun AppBottomBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Watchlist")

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(if (item == "Home") Icons.Filled.Home else Icons.Filled.Star, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
fun MovieList(modifier: Modifier = Modifier) {
    val movies = getMovies()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp) // Adds space between items
    ) {
        items(movies) { movie ->
            MovieRow(movie)
        }
    }
}

@Preview
@Composable
fun MovieAppPreview() {
    AppScaffold()
}