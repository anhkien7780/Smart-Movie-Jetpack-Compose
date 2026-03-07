package com.kiennv31.smartmovie.presentation.screen.genre_movie

import com.kiennv31.smartmovie.domain.model.Movie

data class GenreMoviesState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
