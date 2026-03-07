package com.kiennv31.smartmovie.presentation.screen.detail

import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieDetail

data class MovieDetailState(
    val movieDetail: MovieDetail? = null,
    val cast: List<Cast> = emptyList(),
    val similarMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
