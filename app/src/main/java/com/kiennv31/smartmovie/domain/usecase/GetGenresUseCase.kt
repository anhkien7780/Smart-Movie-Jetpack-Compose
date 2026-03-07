package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.MovieGenre
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Resource<List<MovieGenre>> {
        return repository.getGenres()
    }
}
