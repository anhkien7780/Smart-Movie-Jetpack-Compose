package com.kiennv31.smartmovie.data.remote.api

import com.kiennv31.smartmovie.data.remote.dto.GenreListDto
import retrofit2.http.GET

interface GenreApi {

    @GET(ApiConstants.GENRE_MOVIE_LIST)
    suspend fun getMovieGenres(): GenreListDto
}
