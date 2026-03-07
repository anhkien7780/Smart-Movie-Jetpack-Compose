package com.kiennv31.smartmovie.presentation.screen.home.page.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.usecase.DeleteFavoriteMovieUseCase
import com.kiennv31.smartmovie.domain.usecase.GetAllFavoriteMoviesUseCase
import com.kiennv31.smartmovie.domain.usecase.GetMoviesUseCase
import com.kiennv31.smartmovie.domain.usecase.InsertFavoriteMovieUseCase
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
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesPageState())
    val state = _state.asStateFlow()

    private var favoriteMovieIds = setOf<Int>()

    init {
        observeFavorites()
        refreshAll()
    }

    private fun observeFavorites() {
        getAllFavoriteMoviesUseCase().onEach { favorites ->
            favoriteMovieIds = favorites.map { it.id }.toSet()
            _state.update { currentState ->
                currentState.copy(
                    popular = currentState.popular.map { it.copy(isFavorite = favoriteMovieIds.contains(it.id)) },
                    topRated = currentState.topRated.map { it.copy(isFavorite = favoriteMovieIds.contains(it.id)) },
                    upcoming = currentState.upcoming.map { it.copy(isFavorite = favoriteMovieIds.contains(it.id)) },
                    nowPlaying = currentState.nowPlaying.map { it.copy(isFavorite = favoriteMovieIds.contains(it.id)) }
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

    fun refreshAll() {
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

                when (popularResource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = popularResource.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        popularMovies = popularResource.data.map { 
                            it.copy(isFavorite = favoriteMovieIds.contains(it.id)) 
                        }
                    }
                }

                when (topRatedResource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = topRatedResource.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        topRatedMovies = topRatedResource.data.map { 
                            it.copy(isFavorite = favoriteMovieIds.contains(it.id)) 
                        }
                    }
                }

                when (upcomingResource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = upcomingResource.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        upcomingMovies = upcomingResource.data.map { 
                            it.copy(isFavorite = favoriteMovieIds.contains(it.id)) 
                        }
                    }
                }

                when (nowPlayingResource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = nowPlayingResource.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        nowPlayingMovies = nowPlayingResource.data.map { 
                            it.copy(isFavorite = favoriteMovieIds.contains(it.id)) 
                        }
                    }
                }


                if (_state.value.error == null) {
                    _state.update {
                        it.copy(
                            popular = popularMovies,
                            topRated = topRatedMovies,
                            upcoming = upcomingMovies,
                            nowPlaying = nowPlayingMovies,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
