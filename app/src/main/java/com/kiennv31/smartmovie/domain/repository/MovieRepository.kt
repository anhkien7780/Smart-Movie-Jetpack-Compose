package com.kiennv31.smartmovie.domain.repository

import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.model.MovieDetail
import com.kiennv31.smartmovie.domain.model.MovieGenre

interface MovieRepository {
    suspend fun getMovies(category: MovieCategory, page: Int): Resource<List<Movie>>
    suspend fun searchMovies(query: String): Resource<List<Movie>>
    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail>
    suspend fun getMovieCast(movieId: Int): Resource<List<Cast>>
    suspend fun getGenres(): Resource<List<MovieGenre>>
    suspend fun getSimilarMovies(movieId: Int, page: Int): Resource<List<Movie>>
    suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<List<Movie>>
}
