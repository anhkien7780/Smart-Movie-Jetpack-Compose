package com.kiennv31.smartmovie.data.remote.api

import com.kiennv31.smartmovie.data.remote.dto.MovieCreditsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CastApi {

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCreditsDto
}
