package com.kiennv31.smartmovie.presentation.screen.home.page.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kiennv31.smartmovie.presentation.screen.home.common.MovieListPage

@Composable
fun PopularPage(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PopularViewModel,
    isGridView: Boolean = true
) {
    val state by viewModel.state.collectAsState()

    MovieListPage(
        state = state,
        onLoadMore = { viewModel.loadMore() },
        onRetryClick = { viewModel.refresh() },
        onFavoriteClick = { viewModel.onFavoriteClick(it) },
        onItemClick = onMovieClick,
        modifier = modifier,
        isGridView = isGridView
    )
}
