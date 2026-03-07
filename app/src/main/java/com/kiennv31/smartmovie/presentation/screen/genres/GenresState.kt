package com.kiennv31.smartmovie.presentation.screen.genres

import com.kiennv31.smartmovie.domain.model.MovieGenre

data class GenresState(
    val genres: List<MovieGenre> = emptyList(),
    val filteredGenres: List<MovieGenre> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
