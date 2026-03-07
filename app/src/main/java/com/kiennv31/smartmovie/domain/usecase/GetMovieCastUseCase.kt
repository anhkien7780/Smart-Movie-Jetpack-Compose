package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<List<Cast>> {
        return repository.getMovieCast(movieId)
    }
}
