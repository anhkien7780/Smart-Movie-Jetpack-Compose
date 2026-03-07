package com.kiennv31.smartmovie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val isFavorite: Boolean,
    val posterPath: String,
    val overview: String = "",
    val backdropPath: String? = null,
    val voteAverage: Double = 0.0,
    val genreNames: List<String> = emptyList()
)
