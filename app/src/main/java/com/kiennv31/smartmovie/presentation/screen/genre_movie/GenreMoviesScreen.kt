package com.kiennv31.smartmovie.presentation.screen.genre_movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.presentation.common.BaseAppBar
import com.kiennv31.smartmovie.presentation.common.BaseLoadingOverlay
import com.kiennv31.smartmovie.presentation.screen.genre_movie.common.GenreMoviesGrid

@Composable
fun GenreMoviesScreen(
    genreName: String,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: GenreMoviesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            BaseAppBar(
                title = genreName,
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
        GenreMoviesContent(
            state = state,
            onMovieClick = onMovieClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun GenreMoviesContent(
    state: GenreMoviesState,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
            GenreMoviesGrid(
                movies = state.movies,
                onMovieClick = onMovieClick
            )
        }
    }
}
