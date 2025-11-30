package com.example.lab_week_12

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.lab_week_12.api.MovieService
import com.example.lab_week_12.model.Movie

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "4bf75ebecbd25b57f0c8485e3b61c04e"

    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    suspend fun fetchMovies() {
        try {
            val response = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(response.results)
        } catch (exception: Exception) {
            errorLiveData.postValue("An error occurred: ${exception.message}")
        }
    }
}
