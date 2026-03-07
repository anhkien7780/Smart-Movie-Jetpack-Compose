package com.kiennv31.smartmovie.presentation.screen.home.page.movies

import com.kiennv31.smartmovie.domain.model.Movie

data class MoviesPageState(
    val popular: List<Movie> = emptyList(),
    val topRated: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)