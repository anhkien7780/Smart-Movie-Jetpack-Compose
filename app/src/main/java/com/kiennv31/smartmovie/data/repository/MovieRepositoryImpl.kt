package com.kiennv31.smartmovie.data.repository

import android.util.Log
import com.kiennv31.smartmovie.core.utils.Resource
import com.kiennv31.smartmovie.data.mapper.toCast
import com.kiennv31.smartmovie.data.mapper.toMovie
import com.kiennv31.smartmovie.data.mapper.toMovieDetail
import com.kiennv31.smartmovie.data.mapper.toMovieGenre
import com.kiennv31.smartmovie.data.remote.api.ApiConstants
import com.kiennv31.smartmovie.data.remote.api.CastApi
import com.kiennv31.smartmovie.data.remote.api.GenreApi
import com.kiennv31.smartmovie.data.remote.api.MovieApi
import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.model.Movie
import com.kiennv31.smartmovie.domain.model.MovieCategory
import com.kiennv31.smartmovie.domain.model.MovieDetail
import com.kiennv31.smartmovie.domain.model.MovieGenre
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val castApi: CastApi,
    private val genreApi: GenreApi
) : MovieRepository {

    private val TAG = "MovieRepositoryImpl"

    override suspend fun getMovies(
        category: MovieCategory,
        page: Int
    ): Resource<List<Movie>> {
        Log.d(TAG, "getMovies: category=$category, page=$page")
        return try {
            val response = when (category) {
                MovieCategory.POPULAR -> movieApi.getPopularMovies(page = page)
                MovieCategory.TOP_RATED -> movieApi.getTopRatedMovies(page = page)
                MovieCategory.UPCOMING -> movieApi.getUpcomingMovies(page = page)
                MovieCategory.NOW_PLAYING -> movieApi.getNowPlayingMovies(page = page)
            }
            val movies = response.results.map { it.toMovie() }
            Log.d(
                TAG,
                "getMovies success: fetched ${movies.size} movies for $category"
            )
            Resource.Success(movies)
        } catch (e: IOException) {
            Log.e(TAG, "getMovies error: ${e.message}", e)
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Log.e(TAG, "getMovies error: ${e.message}", e)
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun searchMovies(query: String): Resource<List<Movie>> {
        return try {
            val genresResult = getGenres()
            val genres = if (genresResult is Resource.Success) genresResult.data else emptyList()
            val movies = movieApi.searchMovies(query = query).results.map { dto ->
                val movie = dto.toMovie()
                movie.copy(
                    genreNames = dto.genreIds?.map { id ->
                        genres.find { it.id == id }?.name ?: ""
                    } ?: emptyList()
                )
            }
            Resource.Success(movies)
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> {
        return try {
            Resource.Success(movieApi.getMovieDetail(movieId).toMovieDetail())
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMovieCast(movieId: Int): Resource<List<Cast>> {
        return try {
            Resource.Success(castApi.getMovieCredits(movieId).cast.map { it.toCast() })
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getGenres(): Resource<List<MovieGenre>> {
        return try {
            val genres = genreApi.getMovieGenres().genres.map { it.toMovieGenre() }
            val updatedGenres = genres.map { genre ->
                try {
                    val firstMovieResponse = movieApi.getMoviesByGenre(genre.id, 1)
                    val firstMovie = firstMovieResponse.results.firstOrNull()
                    genre.copy(
                        posterPath = if (firstMovie != null)
                            ApiConstants.IMAGE_BASE_URL + "w500" + firstMovie.backdropPath
                        else ""
                    )
                } catch (e: Exception) {
                    genre
                }
            }
            Resource.Success(updatedGenres)
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int): Resource<List<Movie>> {
        return try {
            val genresResult = getGenres()
            val genres = if (genresResult is Resource.Success) genresResult.data else emptyList()
            val movies = movieApi.getSimilarMovies(movieId, page).results.map { dto ->
                val movie = dto.toMovie()
                movie.copy(
                    genreNames = dto.genreIds?.map { id ->
                        genres.find { it.id == id }?.name ?: ""
                    } ?: emptyList()
                )
            }
            Resource.Success(movies)
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<List<Movie>> {
        return try {
            val genresResult = getGenres()
            val genres = if (genresResult is Resource.Success) genresResult.data else emptyList()
            val movies = movieApi.getMoviesByGenre(genreId, page).results.map { dto ->
                val movie = dto.toMovie()
                movie.copy(
                    genreNames = dto.genreIds?.map { id ->
                        genres.find { it.id == id }?.name ?: ""
                    } ?: emptyList()
                )
            }
            Resource.Success(movies)
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
