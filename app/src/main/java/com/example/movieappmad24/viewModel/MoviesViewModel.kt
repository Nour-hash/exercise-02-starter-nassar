package com.example.movieappmad24.viewModel
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

class MoviesViewModel : ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    fun toggleIsFavouriteState(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
    }


}
