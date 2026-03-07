package com.kiennv31.smartmovie.presentation.screen.detail.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.MovieDetail

@Composable
fun MovieHeaderSection(movieDetail: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_big))
    ) {
        AsyncImage(
            model = movieDetail.posterPath,
            contentDescription = movieDetail.title,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.movie_poster_detail_width))
                .aspectRatio(2 / 3f)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.radius_medium))),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_big)))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movieDetail.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movieDetail.genre.joinToString(" | ") { it.name },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < (movieDetail.rate / 2).toInt()) colorResource(
                            id = R.color.star_active
                        ) else Color.LightGray,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_small))
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(
                        id = R.string.rating_format,
                        movieDetail.rate
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Text(
                text = stringResource(
                    id = R.string.language,
                    movieDetail.language
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = buildString {
                    append(movieDetail.releaseDate)

                    if (movieDetail.originCountry.isNotEmpty()) {
                        append(" (")
                        append(movieDetail.originCountry.joinToString(", "))
                        append(")")
                    }

                    if (movieDetail.runtime > 0) {
                        val hours = movieDetail.runtime / 60
                        val minutes = movieDetail.runtime % 60
                        append(" ")
                        append(
                            stringResource(
                                id = R.string.runtime_format,
                                hours,
                                minutes
                            )
                        )
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}