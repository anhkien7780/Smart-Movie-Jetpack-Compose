package com.kiennv31.smartmovie.presentation.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.presentation.common.BaseAppBar
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.common.SearchBar
import com.kiennv31.smartmovie.presentation.screen.search.common.SearchMovieItem

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isSearchActive by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(id = R.string.search),
                actionIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.cyan_primary)
                    )
                },
                onActionClick = {
                    isSearchActive = !isSearchActive
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SearchBar(
                    query = state.query,
                    hint = stringResource(id = R.string.search_movie),
                    onQueryChange = { viewModel.onQueryChange(it) },
                    onClearClick = { viewModel.clearSearch() },
                    onCancelClick = {
                        isSearchActive = false
                    }
                )
            }

            SearchScreenContent(
                state = state,
                onMovieClick = onMovieClick,
                onRetryClick = { viewModel.onQueryChange(state.query) }
            )
        }
    }
}

@Composable
private fun SearchScreenContent(
    state: SearchState,
    onMovieClick: (Int) -> Unit,
    onRetryClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                BaseLoadingOverlay()
            }

            state.error != null -> {
                ErrorSearchContent(
                    error = state.error,
                    onRetryClick = onRetryClick
                )
            }

            state.movies.isEmpty() && state.query.isNotEmpty() -> {
                EmptySearchContent()
            }

            else -> {
                SuccessSearchContent(
                    movies = state.movies,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun SuccessSearchContent(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_big)
        )
    ) {
        items(movies) { movie ->
            SearchMovieItem(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}

@Composable
private fun EmptySearchContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.no_results),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorSearchContent(
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
            Text(text = stringResource(id = R.string.reload))
        }
    }
}
