package com.kiennv31.smartmovie.data.remote.dto

data class GenreListDto(
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)
