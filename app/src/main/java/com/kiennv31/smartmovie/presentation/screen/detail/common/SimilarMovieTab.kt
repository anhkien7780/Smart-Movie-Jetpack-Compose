package com.kiennv31.smartmovie.presentation.screen.detail.common

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import com.kiennv31.smartmovie.domain.model.Movie

fun LazyGridScope.SimilarMovieTab(
    similarMovies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    items(
        items = similarMovies,
        span = { GridItemSpan(maxLineSpan) }
    ) { movie ->
        SimilarMovieItem(
            movie = movie,
            onClick = { onMovieClick(movie.id) }
        )
    }
}
