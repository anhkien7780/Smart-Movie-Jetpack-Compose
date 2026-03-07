package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.repository.FavoriteMovieRepository
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        category: MovieCategory,
        page: Int
    ): Resource<List<Movie>> {
        return movieRepository.getMovies(category, page)
    }
}
