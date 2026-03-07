package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int = 1): Resource<List<Movie>> {
        return repository.getSimilarMovies(movieId, page)
    }
}
