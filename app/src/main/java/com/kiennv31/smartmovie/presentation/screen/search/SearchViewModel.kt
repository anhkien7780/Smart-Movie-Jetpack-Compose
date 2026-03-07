package com.kiennv31.smartmovie.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
        searchJob?.cancel()
        if (newQuery.isBlank()) {
            _state.update { it.copy(movies = emptyList(), isLoading = false) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(500)
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                when (val result = searchMoviesUseCase(newQuery)) {
                    is Resource.Success -> {
                        _state.update { it.copy(movies = result.data, isLoading = false) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(error = result.message, isLoading = false) }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSearch() {
        _state.update { it.copy(query = "", movies = emptyList(), isLoading = false, error = null) }
    }
}
