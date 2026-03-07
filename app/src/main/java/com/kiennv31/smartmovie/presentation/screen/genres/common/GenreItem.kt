package com.kiennv31.smartmovie.presentation.screen.genres.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.MovieGenre

@Composable
fun GenreItem(
    genre: MovieGenre,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.genre_item_height))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.radius_medium)))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = genre.posterPath,
            contentDescription = genre.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        ),
                        startY = 300f
                    )
                )
        )

        Text(
            text = genre.name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.padding_big)),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp
            )
        )
    }
}
