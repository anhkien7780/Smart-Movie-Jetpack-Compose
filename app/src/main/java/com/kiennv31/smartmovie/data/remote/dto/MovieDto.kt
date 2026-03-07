package com.kiennv31.smartmovie.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListDto(
    val results: List<MovieDto>,
    val page: Int,
    @param:Json(name = DtoConstants.TOTAL_PAGES) val totalPages: Int,
    @param:Json(name = DtoConstants.TOTAL_RESULTS) val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @param:Json(name = DtoConstants.POSTER_PATH) val posterPath: String?,
    @param:Json(name = DtoConstants.BACKDROP_PATH) val backdropPath: String?,
    @param:Json(name = DtoConstants.RELEASE_DATE) val releaseDate: String?,
    @param:Json(name = DtoConstants.VOTE_AVERAGE) val voteAverage: Double,
    @param:Json(name = DtoConstants.GENRE_IDS) val genreIds: List<Int>?
)

@JsonClass(generateAdapter = true)
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @param:Json(name = DtoConstants.POSTER_PATH) val posterPath: String?,
    @param:Json(name = DtoConstants.BACKDROP_PATH) val backdropPath: String?,
    @param:Json(name = DtoConstants.RELEASE_DATE) val releaseDate: String?,
    @param:Json(name = DtoConstants.ORIGINAL_LANGUAGE) val originalLanguage: String?,
    @param:Json(name = DtoConstants.VOTE_AVERAGE) val voteAverage: Double,
    @param:Json(name = DtoConstants.RUNTIME) val runtime: Int?,
    val genres: List<GenreDto>?,
    @param:Json(name = DtoConstants.ORIGIN_COUNTRY) val originCountry: List<String>?
)

@JsonClass(generateAdapter = true)
data class ConfigurationDto(
    val images: ImagesConfigDto
)

@JsonClass(generateAdapter = true)
data class ImagesConfigDto(
    @param:Json(name = DtoConstants.BASE_URL) val baseUrl: String,
    @param:Json(name = DtoConstants.SECURE_BASE_URL) val secureBaseUrl: String,
    @param:Json(name = DtoConstants.BACKDROP_SIZES) val backdropSizes: List<String>,
    @param:Json(name = DtoConstants.POSTER_SIZES) val posterSizes: List<String>
)
