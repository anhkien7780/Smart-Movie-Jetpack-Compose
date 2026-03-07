package com.kiennv31.smartmovie.data.mapper

import com.kiennv31.smartmovie.data.local.entity.FavoriteMovieEntity
import com.kiennv31.smartmovie.data.remote.api.ApiConstants
import com.kiennv31.smartmovie.data.remote.dto.MovieDto
import com.kiennv31.smartmovie.domain.model.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        isFavorite = false,
        posterPath = ApiConstants.IMAGE_BASE_URL + "w500" + posterPath,
        backdropPath = ApiConstants.IMAGE_BASE_URL + "w780" + (backdropPath ?: posterPath),
        voteAverage = voteAverage,
        overview = overview
    )
}

fun FavoriteMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        isFavorite = true,
        overview = overview
    )
}

fun Movie.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        isFavorite = true,
        overview = overview
    )
}
