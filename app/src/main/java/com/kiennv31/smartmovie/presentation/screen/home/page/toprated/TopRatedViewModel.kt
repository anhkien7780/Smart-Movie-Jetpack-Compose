package com.kiennv31.smartmovie.presentation.screen.home.page.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.usecase.DeleteFavoriteMovieUseCase
import com.kiennv31.smartmovie.domain.usecase.GetAllFavoriteMoviesUseCase
import com.kiennv31.smartmovie.domain.usecase.GetMoviesUseCase
import com.kiennv31.smartmovie.domain.usecase.InsertFavoriteMovieUseCase
import com.kiennv31.smartmovie.presentation.screen.home.common.MovieListPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListPageState())
    val state = _state.asStateFlow()

    private var favoriteMovieIds = setOf<Int>()

    init {
        observeFavorites()
        loadMovies()
    }

    private fun observeFavorites() {
        getAllFavoriteMoviesUseCase().onEach { favorites ->
            favoriteMovieIds = favorites.map { it.id }.toSet()
            _state.update { currentState ->
                currentState.copy(
                    movies = currentState.movies.map {
                        it.copy(isFavorite = favoriteMovieIds.contains(it.id))
                    }
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                deleteFavoriteMovieUseCase(movie)
            } else {
                insertFavoriteMovieUseCase(movie)
            }
        }
    }

    fun loadMovies() {
        if (_state.value.isLoading || _state.value.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = async {
                    getMoviesUseCase(
                        MovieCategory.TOP_RATED,
                        _state.value.page
                    )
                }.await()

                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        if (result.data.isEmpty()) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    endReached = true
                                )
                            }
                        } else {
                            val newMovies = result.data.map {
                                it.copy(isFavorite = favoriteMovieIds.contains(it.id))
                            }
                            _state.update {
                                it.copy(
                                    movies = it.movies + newMovies,
                                    isLoading = false,
                                    page = it.page + 1
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun loadMore() {
        if (_state.value.isLoadMore || _state.value.endReached || _state.value.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoadMore = true) }
            try {
                val result = async {
                    getMoviesUseCase(
                        MovieCategory.TOP_RATED,
                        _state.value.page
                    )
                }.await()

                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoadMore = false,
                                error = result.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        if (result.data.isEmpty()) {
                            _state.update {
                                it.copy(
                                    isLoadMore = false,
                                    endReached = true
                                )
                            }
                        } else {
                            val newMovies = result.data.map {
                                it.copy(isFavorite = favoriteMovieIds.contains(it.id))
                            }
                            _state.update {
                                it.copy(
                                    movies = it.movies + newMovies,
                                    isLoadMore = false,
                                    page = it.page + 1
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadMore = false, error = e.message) }
            }
        }
    }

    fun refresh() {
        _state.update { MovieListPageState() }
        loadMovies()
    }
}
