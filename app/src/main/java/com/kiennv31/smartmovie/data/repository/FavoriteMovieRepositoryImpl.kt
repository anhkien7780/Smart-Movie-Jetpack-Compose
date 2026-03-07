package com.kiennv31.smartmovie.data.repository

import com.kiennv31.smartmovie.data.local.dao.FavoriteMovieDao
import com.kiennv31.smartmovie.data.mapper.toFavoriteMovieEntity
import com.kiennv31.smartmovie.data.mapper.toMovie
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteMovieRepository {
    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovieDao.insertFavoriteMovie(movie.toFavoriteMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovieDao.deleteFavoriteMovie(movie.toFavoriteMovieEntity())
    }

    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavoriteMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }

    override fun isMovieFavorite(id: Int): Flow<Boolean> {
        return favoriteMovieDao.isMovieFavorite(id)
    }
}
