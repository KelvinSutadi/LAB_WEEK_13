package com.example.lab_week_12

import android.app.Application
import com.example.lab_week_12.api.MovieService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Create a MovieService instance (API interface)
        val movieService = retrofit.create(MovieService::class.java)

        // Create a MovieRepository instance
        movieRepository = MovieRepository(movieService)
    }
}