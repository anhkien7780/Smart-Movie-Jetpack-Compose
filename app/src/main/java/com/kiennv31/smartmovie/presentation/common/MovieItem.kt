package com.kiennv31.smartmovie.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    isGridView: Boolean = true,
    onFavoriteClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    if (isGridView) {
        VerticalMovieItem(movie, modifier, onFavoriteClick, onItemClick)
    } else {
        HorizontalMovieItem(movie, modifier, onFavoriteClick, onItemClick)
    }
}

@Composable
private fun VerticalMovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.clickable { onItemClick(movie.id) }
    ) {
        Box {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_small))),
                contentScale = ContentScale.Crop
            )
            FavoriteButton(
                isFavorite = movie.isFavorite,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
        Text(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)),
            text = movie.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HorizontalMovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_small)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxHeight()
                        .weight(1f)
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                        text = movie.overview,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            FavoriteButton(
                isFavorite = movie.isFavorite,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(R.string.favorite),
        tint = if (isFavorite) Color.Yellow else Color.Gray,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .size(dimensionResource(id = R.dimen.icon_size_medium))
            .clickable { onFavoriteClick() }
            .padding(4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    val movie = Movie(
        id = 1,
        title = "Inception",
        isFavorite = false,
        posterPath = "https://example.com/poster.jpg",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
    )
    Column(modifier = Modifier.padding(16.dp)) {
        MovieItem(movie = movie, isGridView = true)
        Spacer(modifier = Modifier.height(16.dp))
        MovieItem(movie = movie, isGridView = false)
    }
}
