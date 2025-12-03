package com.example.lab_week_13

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val movieRepository: MovieRepository)
    : ViewModel() {

    private val _popularMovies = MutableStateFlow(
        emptyList<Movie>()
    )
    // PERBAIKAN: Ubah ke LiveData agar DataBinding di XML bisa membacanya
    val popularMovies: LiveData<List<Movie>> = _popularMovies.asLiveData()

    private val _error = MutableStateFlow("")
    val error: LiveData<String> = _error.asLiveData()

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .map { movies ->
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                    movies
                        .filter { movie ->
                            movie.releaseDate?.startsWith(currentYear) == true
                        }
                        .sortedByDescending { it.popularity }
                }
                .catch {
                    _error.value = "An exception occurred: ${it.message}"
                }
                .collect {
                    _popularMovies.value = it
                }
        }
    }
}