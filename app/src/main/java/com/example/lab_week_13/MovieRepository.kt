package com.example.lab_week_13

import android.util.Log
import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) {
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            val movieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()

            if (savedMovies.isEmpty()) {
                try {
                    val apiKey = "4bf75ebecbd25b57f0c8485e3b61c04e"
                    val movies = movieService.getPopularMovies(apiKey).results
                    movieDao.addMovies(movies)
                    emit(movies)
                } catch (e: Exception) {
                    emit(emptyList())
                }
            } else {
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchMoviesFromNetwork() {
        val movieDao = movieDatabase.movieDao()
        try {
            val apiKey = "4bf75ebecbd25b57f0c8485e3b61c04e"
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results

            movieDao.addMovies(moviesFetched)

            Log.d("MovieRepository", "Success fetching data from network")
        } catch (exception: Exception) {
            Log.d("MovieRepository", "An error occurred: ${exception.message}")
        }
    }
}