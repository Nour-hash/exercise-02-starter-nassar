package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.viewModel.MoviesViewModel
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleTopAppBar


@Composable
fun DetailScreen(navController: NavController, movieId: String, moviesViewModel: MoviesViewModel) {

    val movie = moviesViewModel.movieList.find { it.id == movieId }

    if (movie == null) {
        Text("Movie not found")
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopAppBar(navController = navController, title = movie.title, backButton = true)
        },
        content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                item {
                    MovieRow(
                        movie = movie,
                        onFavoriteClick = { moviesViewModel.toggleIsFavouriteState(movie = movie) }

                    )
                }
                item {
                    ExoPlayer(movieTrailer = movie.trailer)
                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(start = 8.dp, top = 6.dp, bottom = 6.dp)
                    ) {
                        items(movie.images) { image ->
                            MoviePicture(resourceLink = image, title = movie.title)
                        }
                    }
                }
            }

        }
    )

}


@Composable
fun MoviePicture(resourceLink: String, title: String) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        modifier = Modifier
            .padding(all = 15.dp)
    ) {
        AsyncImage(
            model = resourceLink,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_image),
            contentDescription = "$title Image",
            modifier = Modifier
                .aspectRatio(ratio = 1f)
        )
    }
}

@Composable
fun ExoPlayer(
    movieTrailer: String
) {
    var lifecycle by remember {
        mutableStateOf(value = Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    val trailer = MediaItem.fromUri("android.resource://${context.packageName}/$movieTrailer")
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(trailer)
            prepare()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer = observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer = observer)
        }
    }
    var videoPaused by remember {
        mutableStateOf(value = true)
    }

    AndroidView(
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = { playerView ->
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    playerView.onResume()
                    if (!videoPaused) {
                        exoPlayer.play()
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    playerView.onPause()
                    videoPaused = !exoPlayer.isPlaying
                    exoPlayer.pause()
                }

                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 16f / 9f)
    )
}

@Preview
@Composable
fun MovieApp_Detail_Preview() {
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()
    DetailScreen(navController,"tt0903747" , moviesViewModel)
}
