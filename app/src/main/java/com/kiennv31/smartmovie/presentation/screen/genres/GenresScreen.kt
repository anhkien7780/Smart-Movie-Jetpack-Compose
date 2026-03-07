package com.kiennv31.smartmovie.presentation.screen.genres

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.kiennv31.smartmovie.presentation.common.BaseAppBar
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.screen.genres.common.GenreItem
import com.kiennv31.smartmovie.presentation.common.SearchBar

@Composable
fun GenresScreen(
    onGenreClick: (Int, String) -> Unit,
    viewModel: GenresViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(id = R.string.genres),
                actionIcon = {
                    Icon(
                        imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.cyan_primary)
                    )
                },
                onActionClick = {
                    isSearchActive = !isSearchActive
                    if (!isSearchActive) viewModel.onSearchQueryChange("")
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
                    query = state.searchQuery,
                    hint = stringResource(id = R.string.search_genre),
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    onClearClick = { viewModel.onSearchQueryChange("") },
                    onCancelClick = {
                        isSearchActive = false
                        viewModel.onSearchQueryChange("")
                    }
                )
            }

            GenreScreenContent(
                state = state,
                onGenreClick = onGenreClick
            )
        }
    }
}

@Composable
private fun GenreScreenContent(
    state: GenresState,
    onGenreClick: (Int, String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            BaseLoadingOverlay()
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.error,
                    color = Color.Red
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_big)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                items(state.filteredGenres) { genre ->
                    GenreItem(
                        genre = genre,
                        onClick = { onGenreClick(genre.id, genre.name) }
                    )
                }
            }
        }
    }
}
