package com.kiennv31.smartmovie.data.mapper

import com.kiennv31.smartmovie.data.remote.api.ApiConstants
import com.kiennv31.smartmovie.data.remote.dto.CastDto
import com.kiennv31.smartmovie.data.remote.dto.GenreDto
import com.kiennv31.smartmovie.data.remote.dto.MovieDetailDto
import com.kiennv31.smartmovie.domain.model.Cast
import com.kiennv31.smartmovie.domain.model.MovieDetail
import com.kiennv31.smartmovie.domain.model.MovieGenre
import java.text.SimpleDateFormat
import java.util.Locale

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        genre = genres?.map { it.toMovieGenre() } ?: emptyList(),
        rate = voteAverage,
        language = formatLanguage(originalLanguage),
        releaseDate = formatReleaseDate(releaseDate),
        overview = overview,
        posterPath = ApiConstants.IMAGE_BASE_URL + "w500" + posterPath,
        runtime = runtime ?: 0,
        originCountry = originCountry ?: emptyList()
    )
}

private fun formatLanguage(languageCode: String?): String {
    if (languageCode.isNullOrEmpty()) return ""
    return try {
        val locale = Locale.forLanguageTag(languageCode)
        locale.getDisplayLanguage(Locale.US)
    } catch (e: Exception) {
        languageCode
    }
}

private fun formatReleaseDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

fun GenreDto.toMovieGenre(): MovieGenre {
    return MovieGenre(
        id = id,
        name = name,
        posterPath = ""
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        id = id,
        name = name,
        profilePath = ApiConstants.IMAGE_BASE_URL + "w500" + profilePath
    )
}
