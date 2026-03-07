package com.kiennv31.smartmovie.data.remote.dto

import com.squareup.moshi.Json

data class MovieCreditsDto(
    val id: Int,
    val cast: List<CastDto>
)

data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    @param:Json(name = DtoConstants.PROFILE_PATH) val profilePath: String?
)
