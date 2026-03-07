package com.kiennv31.smartmovie.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.usecase.GetMovieCastUseCase
import com.kiennv31.smartmovie.domain.usecase.GetMovieDetailUseCase
import com.kiennv31.smartmovie.domain.usecase.GetSimilarMoviesUseCase
import com.kiennv31.smartmovie.presentation.navigation.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    private val route: MovieDetail = savedStateHandle.toRoute()

    private val movieId = route.movieId

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val detailDeferred = async { getMovieDetailUseCase(movieId) }
                val castDeferred = async { getMovieCastUseCase(movieId) }
                val similarDeferred = async { getSimilarMoviesUseCase(movieId) }

                val detailResult = detailDeferred.await()
                val castResult = castDeferred.await()
                val similarResult = similarDeferred.await()

                if (detailResult is Resource.Error) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = detailResult.message
                        )
                    }
                    return@launch
                }

                _state.update {
                    it.copy(
                        movieDetail = (detailResult as? Resource.Success)?.data,
                        cast = if (castResult is Resource.Success) castResult.data else emptyList(),
                        similarMovies = if (similarResult is Resource.Success) similarResult.data else emptyList(),
                        isLoading = false
                    )
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
