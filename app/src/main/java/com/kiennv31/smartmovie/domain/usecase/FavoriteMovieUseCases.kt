package com.kiennv31.smartmovie.domain.usecase

import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavoriteMovieUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movie: Movie) =
        repository.insertFavoriteMovie(movie)
}

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movie: Movie) =
        repository.deleteFavoriteMovie(movie)
}

class GetAllFavoriteMoviesUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getAllFavoriteMovies()
}

class IsMovieFavoriteUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = repository.isMovieFavorite(id)
}
