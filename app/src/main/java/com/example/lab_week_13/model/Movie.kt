package com.example.lab_week_13.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "movies")
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey
    val id: Int,

    val title: String?,

    val overview: String?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "backdrop_path")
    val backdropPath: String? = "",

    @Json(name = "release_date")
    val releaseDate: String?,

    val popularity: Double
)