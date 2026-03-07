package com.kiennv31.smartmovie.presentation.screen.home.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.common.MovieItem

@Composable
fun MovieListPage(
    state: MovieListPageState,
    onLoadMore: () -> Unit,
    onRetryClick: () -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isGridView: Boolean = true
) {
    val gridState = rememberLazyGridState()

    val currentState by rememberUpdatedState(state)
    val currentOnLoadMore by rememberUpdatedState(onLoadMore)

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= currentState.movies.size - 5 &&
                    !currentState.endReached &&
                    !currentState.isLoading &&
                    !currentState.isLoadMore
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && currentState.movies.isNotEmpty()) {
            currentOnLoadMore()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading && state.movies.isEmpty() -> {
                BaseLoadingOverlay()
            }

            state.error != null && state.movies.isEmpty() -> {
                ErrorContent(
                    error = state.error,
                    onRetryClick = onRetryClick
                )
            }

            else -> {
                SuccessContent(
                    state = state,
                    gridState = gridState,
                    isGridView = isGridView,
                    onFavoriteClick = onFavoriteClick,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error, color = Color.Red)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        TextButton(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun SuccessContent(
    state: MovieListPageState,
    gridState: LazyGridState,
    isGridView: Boolean,
    onFavoriteClick: (Movie) -> Unit,
    onItemClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(if (isGridView) 2 else 1),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = state.movies,
            key = { index, movie -> "${movie.id}_$index" }
        ) { _, movie ->
            MovieItem(
                movie = movie,
                isGridView = isGridView,
                onFavoriteClick = { onFavoriteClick(movie) },
                onItemClick = onItemClick
            )
        }

        if (state.isLoadMore) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadMoreIndicator()
            }
        }
    }
}

@Composable
private fun LoadMoreIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}
