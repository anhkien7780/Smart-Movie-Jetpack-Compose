package com.kiennv31.smartmovie.presentation.screen.genre_movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.usecase.GetMoviesByGenreUseCase
import com.kiennv31.smartmovie.presentation.navigation.GenreDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreMoviesViewModel @Inject constructor(
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GenreMoviesState())
    val state = _state.asStateFlow()

    private val route: GenreDetail = savedStateHandle.toRoute()
    private val genreId = route.genreId

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                when (val result = getMoviesByGenreUseCase(genreId)) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                movies = result.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}
