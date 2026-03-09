package com.kiennv31.smartmovie.presentation.screen.detail.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kiennv31.smartmovie.R
import com.kiennv31.smartmovie.domain.model.Cast

@Composable
fun CastTab(
    cast: List<Cast>,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = modifier
            .height(dimensionResource(id = R.dimen.cast_grid_height))
            .padding(top = dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(cast) { member ->
            CastMemberItem(
                cast = member,
                modifier = Modifier.width(dimensionResource(id = R.dimen.cast_item_width))
            )
        }
    }
}
