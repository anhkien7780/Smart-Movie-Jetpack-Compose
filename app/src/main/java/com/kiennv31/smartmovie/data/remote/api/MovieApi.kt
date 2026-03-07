package com.kiennv31.smartmovie.data.remote.api

import com.kiennv31.smartmovie.data.remote.dto.ConfigurationDto
import com.kiennv31.smartmovie.data.remote.dto.MovieDetailDto
import com.kiennv31.smartmovie.data.remote.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(ApiConstants.CONFIGURATION)
    suspend fun getConfiguration(): ConfigurationDto

    @GET(ApiConstants.POPULAR)
    suspend fun getPopularMovies(
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto

    @GET(ApiConstants.TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto

    @GET(ApiConstants.UPCOMING)
    suspend fun getUpcomingMovies(
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto

    @GET(ApiConstants.NOW_PLAYING)
    suspend fun getNowPlayingMovies(
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto

    @GET(ApiConstants.MOVIE_DETAIL)
    suspend fun getMovieDetail(
        @Path(ApiConstants.MOVIE_ID) movieId: Int
    ): MovieDetailDto

    @GET(ApiConstants.SIMILAR_MOVIES)
    suspend fun getSimilarMovies(
        @Path(ApiConstants.MOVIE_ID) movieId: Int,
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto

    @GET(ApiConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query(ApiConstants.QUERY_QUERY) query: String
    ): MovieListDto

    @GET(ApiConstants.DISCOVER_MOVIE)
    suspend fun getMoviesByGenre(
        @Query(ApiConstants.WITH_GENRES_QUERY) genreId: Int,
        @Query(ApiConstants.PAGE_QUERY) page: Int
    ): MovieListDto
}
