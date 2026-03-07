package com.kiennv31.smartmovie.presentation.screen.home.common

import com.kiennv31.smartmovie.domain.model.Movie

data class MovieListPageState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadMore: Boolean = false,
    val error: String? = null,
    val page: Int = 1,
    val endReached: Boolean = false,
    val isGridView: Boolean = false
)
