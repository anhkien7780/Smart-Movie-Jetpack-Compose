package com.kiennv31.smartmovie.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val genre: List<MovieGenre>,
    val rate: Double,
    val language: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val runtime: Int,
    val originCountry: List<String>
)
