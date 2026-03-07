package com.kiennv31.smartmovie.presentation.screen.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GenresState())
    val state = _state.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                when (val result = getGenresUseCase()) {
                    is Resource.Success -> {
                        val genres = result.data
                        _state.update {
                            it.copy(
                                genres = genres,
                                filteredGenres = genres,
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

    fun onSearchQueryChange(query: String) {
        _state.update { currentState ->
            val filtered = if (query.isBlank()) {
                currentState.genres
            } else {
                currentState.genres.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
            currentState.copy(
                searchQuery = query,
                filteredGenres = filtered
            )
        }
    }
}
