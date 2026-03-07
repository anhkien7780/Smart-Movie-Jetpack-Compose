package com.kiennv31.smartmovie.presentation.screen.search

import com.kiennv31.smartmovie.domain.model.Movie

data class SearchState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
