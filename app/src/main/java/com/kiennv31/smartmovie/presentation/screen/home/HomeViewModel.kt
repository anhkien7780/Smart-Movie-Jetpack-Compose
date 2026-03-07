package com.kiennv31.smartmovie.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _subPagesLoading = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    init {
        loadAllMovies()
    }

    fun updateSubPageLoading(pageIndex: Int, isLoading: Boolean) {
        _subPagesLoading.update { it + (pageIndex to isLoading) }
    }

    private fun isAnyPageLoading(): Boolean {
        return _state.value.isLoading || _subPagesLoading.value.values.any { it }
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val popularDef =
                    async { getMoviesUseCase(MovieCategory.POPULAR, 1) }
                val topRatedDef =
                    async { getMoviesUseCase(MovieCategory.TOP_RATED, 1) }
                val upcomingDef =
                    async { getMoviesUseCase(MovieCategory.UPCOMING, 1) }
                val nowPlayingDef =
                    async { getMoviesUseCase(MovieCategory.NOW_PLAYING, 1) }

                val popularResource = popularDef.await()
                val topRatedResource = topRatedDef.await()
                val upcomingResource = upcomingDef.await()
                val nowPlayingResource = nowPlayingDef.await()

                var popularMovies = emptyList<Movie>()
                var topRatedMovies = emptyList<Movie>()
                var upcomingMovies = emptyList<Movie>()
                var nowPlayingMovies = emptyList<Movie>()

                if (popularResource is Resource.Success) popularMovies = popularResource.data
                if (topRatedResource is Resource.Success) topRatedMovies = topRatedResource.data
                if (upcomingResource is Resource.Success) upcomingMovies = upcomingResource.data
                if (nowPlayingResource is Resource.Success) nowPlayingMovies = nowPlayingResource.data

                val error = when {
                    popularResource is Resource.Error -> popularResource.message
                    topRatedResource is Resource.Error -> topRatedResource.message
                    upcomingResource is Resource.Error -> upcomingResource.message
                    nowPlayingResource is Resource.Error -> nowPlayingResource.message
                    else -> null
                }

                _state.update {
                    it.copy(
                        popularMovies = popularMovies,
                        topRatedMovies = topRatedMovies,
                        upcomingMovies = upcomingMovies,
                        nowPlayingMovies = nowPlayingMovies,
                        isLoading = false,
                        error = error
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun toggleViewMode() {
        if (!isAnyPageLoading()) {
            _state.update { it.copy(isGridView = !it.isGridView) }
        }
    }
}
