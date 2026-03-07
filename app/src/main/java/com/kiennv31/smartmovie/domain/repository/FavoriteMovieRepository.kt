package com.kiennv31.smartmovie.domain.repository

import com.kiennv31.smartmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
    suspend fun insertFavoriteMovie(movie: Movie)
    suspend fun deleteFavoriteMovie(movie: Movie)
    fun getAllFavoriteMovies(): Flow<List<Movie>>
    fun isMovieFavorite(id: Int): Flow<Boolean>
}
