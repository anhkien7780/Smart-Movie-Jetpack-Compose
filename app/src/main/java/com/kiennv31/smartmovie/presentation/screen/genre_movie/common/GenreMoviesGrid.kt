package com.kiennv31.smartmovie.presentation.screen.genre_movie.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.presentation.common.MovieItem

@Composable
fun GenreMoviesGrid(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onItemClick = onMovieClick,
                onFavoriteClick = { /* Handle favorite */ }
            )
        }
    }
}
