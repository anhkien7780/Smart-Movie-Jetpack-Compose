package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String): Resource<List<Movie>> {
        return movieRepository.searchMovies(query)
    }
}
