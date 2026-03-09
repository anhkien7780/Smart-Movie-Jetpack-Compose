package com.kiennv31.smartmovie.presentation.screen.home.page.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.core.ui.theme.SmartMovieTheme
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.common.MovieItem

@Composable
fun MoviesPage(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel,
    isGridView: Boolean = true,
    onSeeAllClick: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    MoviesPageContent(
        modifier = modifier,
        state = state,
        isGridView = isGridView,
        onReloadClick = { viewModel.refreshAll() },
        onSeeAllClick = onSeeAllClick,
        onFavoriteClick = { viewModel.onFavoriteClick(it) },
        onMovieClick = onMovieClick
    )
}

@Composable
private fun MoviesPageContent(
    modifier: Modifier,
    state: MoviesPageState,
    isGridView: Boolean,
    onReloadClick: () -> Unit,
    onSeeAllClick: (Int) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val error = state.error
    when {
        state.isLoading -> {
            BaseLoadingOverlay()
        }

        error != null -> {
            ErrorLoadingContent(
                error = error,
                onReloadClick = onReloadClick
            )
        }

        else -> {
            SuccessLoadingContent(
                modifier = modifier,
                state = state,
                isGridView = isGridView,
                onSeeAllClick = onSeeAllClick,
                onFavoriteClick = onFavoriteClick,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
private fun ErrorLoadingContent(
    error: String,
    onReloadClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error, color = Color.Red)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        TextButton(onClick = onReloadClick) {
            Text(text = stringResource(id = R.string.reload))
        }
    }
}

@Composable
private fun SuccessLoadingContent(
    modifier: Modifier = Modifier,
    state: MoviesPageState,
    isGridView: Boolean,
    onSeeAllClick: (Int) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big))
    ) {
        if (state.popular.isNotEmpty()) {
            item {
                MovieSection(
                    title = stringResource(id = R.string.popular),
                    movies = state.popular.take(4),
                    isGridView = isGridView,
                    onSeeAllClick = { onSeeAllClick(1) },
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = onMovieClick
                )
            }
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_big))) }
        }
        if (state.topRated.isNotEmpty()) {
            item {
                MovieSection(
                    title = stringResource(id = R.string.top_rated),
                    movies = state.topRated.take(4),
                    isGridView = isGridView,
                    onSeeAllClick = { onSeeAllClick(2) },
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = onMovieClick
                )
            }
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_big))) }
        }
        if (state.upcoming.isNotEmpty()) {
            item {
                MovieSection(
                    title = stringResource(id = R.string.upcoming),
                    movies = state.upcoming.take(4),
                    isGridView = isGridView,
                    onSeeAllClick = { onSeeAllClick(3) },
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = onMovieClick
                )
            }
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_big))) }
        }
        if (state.nowPlaying.isNotEmpty()) {
            item {
                MovieSection(
                    title = stringResource(id = R.string.now_playing),
                    movies = state.nowPlaying.take(4),
                    isGridView = isGridView,
                    onSeeAllClick = { onSeeAllClick(4) },
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun MovieSection(
    title: String,
    movies: List<Movie>,
    isGridView: Boolean,
    onSeeAllClick: () -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Column {
        SectionHeader(
            title = title,
            onSeeAllClick = onSeeAllClick
        )
        SectionBody(
            movies = movies,
            isGridView = isGridView,
            onFavoriteClick = onFavoriteClick,
            onMovieClick = onMovieClick
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onSeeAllClick) {
            Text(text = stringResource(id = R.string.see_all))
        }
    }
}

@Composable
private fun SectionBody(
    movies: List<Movie>,
    isGridView: Boolean,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val columns = if (isGridView) 2 else 1
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        movies.chunked(columns).forEach { rowMovies ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {
                rowMovies.forEach { movie ->
                    MovieItem(
                        movie = movie,
                        modifier = Modifier.weight(1f),
                        isGridView = isGridView,
                        onFavoriteClick = { onFavoriteClick(movie) },
                        onItemClick = onMovieClick
                    )
                }
                if (rowMovies.size < columns) {
                    repeat(columns - rowMovies.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesPagePreview() {
    val sampleMovie = Movie(
        id = 1,
        title = "Inception",
        isFavorite = false,
        posterPath = "https://example.com/poster.jpg",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
        voteAverage = 8.8
    )
    val sampleMovies = List(4) { sampleMovie.copy(id = it, title = "Movie $it") }
    val state = MoviesPageState(
        popular = sampleMovies,
        topRated = sampleMovies,
        upcoming = sampleMovies,
        nowPlaying = sampleMovies
    )
    SmartMovieTheme {
        MoviesPageContent(
            modifier = Modifier,
            state = state,
            isGridView = true,
            onReloadClick = {},
            onSeeAllClick = {},
            onFavoriteClick = {},
            onMovieClick = {}
        )
    }
}
