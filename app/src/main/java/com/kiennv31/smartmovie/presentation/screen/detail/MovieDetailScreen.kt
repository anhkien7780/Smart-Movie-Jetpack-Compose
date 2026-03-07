package com.kiennv31.smartmovie.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.core.ui.theme.SmartMovieTheme
import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieDetail
import com.kiennv31.smartmovie.domain.model.MovieGenre
import com.kiennv31.smartmovie.presentation.common.BaseAppBar
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.screen.detail.common.CastMemberItem
import com.kiennv31.smartmovie.presentation.screen.detail.common.MovieHeaderSection
import com.kiennv31.smartmovie.presentation.screen.detail.common.OverviewSection
import com.kiennv31.smartmovie.presentation.screen.detail.common.SimilarMovieItem

@Composable
fun MovieDetailScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    MovieDetailScreenContent(
        state = state,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick
    )
}

@Composable
fun MovieDetailScreenContent(
    state: MovieDetailState,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(id = R.string.movie_detail),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.cancel),
                            tint = colorResource(id = R.color.cyan_primary)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            BaseLoadingOverlay()
        } else {
            state.movieDetail?.let { movieDetail ->
                MovieDetailContent(
                    movieDetail = movieDetail,
                    cast = state.cast,
                    similarMovies = state.similarMovies,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    movieDetail: MovieDetail,
    cast: List<Cast>,
    similarMovies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.cast),
        stringResource(id = R.string.similar_movies)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            MovieHeaderSection(movieDetail)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            OverviewSection(movieDetail.overview)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.cyan_primary),
                edgePadding = 0.dp,
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (selectedTabIndex == index) colorResource(
                                    id = R.color.cyan_primary
                                ) else Color.Gray
                            )
                        }
                    )
                }
            }
        }

        when (selectedTabIndex) {
            0 -> {
                items(cast) { member ->
                    CastMemberItem(member)
                }
            }

            1 -> {
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    SmartMovieTheme {
        MovieDetailScreenContent(
            state = MovieDetailState(
                movieDetail = MovieDetail(
                    id = 1,
                    title = "Interstellar",
                    genre = listOf(
                        MovieGenre(1, "Sci-Fi", ""),
                        MovieGenre(2, "Drama", "")
                    ),
                    rate = 8.7,
                    runtime = 180,
                    originCountry = listOf("USA"),
                    language = "English",
                    releaseDate = "2014-11-07",
                    overview = "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
                    posterPath = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
                ),
                cast = listOf(
                    Cast(
                        1,
                        "Matthew McConaughey",
                        "https://image.tmdb.org/t/p/w500/77S95pY0mO33uLpG0V7Y9uLpG0V.jpg"
                    ),
                    Cast(
                        2,
                        "Anne Hathaway",
                        "https://image.tmdb.org/t/p/w500/7V9V0mO33uLpG0V7Y9uLpG0V.jpg"
                    ),
                    Cast(
                        3,
                        "Jessica Chastain",
                        "https://image.tmdb.org/t/p/w500/7V9V0mO33uLpG0V7Y9uLpG0V.jpg"
                    )
                ),
                similarMovies = emptyList(),
                isLoading = false
            ),
            onBackClick = {},
            onMovieClick = {}
        )
    }
}
