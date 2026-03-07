package com.kiennv31.smartmovie.presentation.screen.home.page.upcoming

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kiennv31.smartmovie.presentation.screen.home.common.MovieListPage

@Composable
fun UpcomingPage(
    modifier: Modifier = Modifier,
    viewModel: UpcomingViewModel,
    isGridView: Boolean = true,
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    MovieListPage(
        state = state,
        onLoadMore = { viewModel.loadMore() },
        onRetryClick = { viewModel.refresh() },
        modifier = modifier,
        isGridView = isGridView,
        onFavoriteClick = { viewModel.onFavoriteClick(it) },
        onItemClick = onMovieClick
    )
}
