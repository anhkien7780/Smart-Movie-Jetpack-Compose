package com.kiennv31.smartmovie.presentation.screen.detail.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kiennv31.smartmovie.R

@Composable
fun OverviewSection(overview: String) {

    var expanded by remember { mutableStateOf(false) }
    var hasOverflow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_big))
    ) {

        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = {
                if (!expanded) {
                    hasOverflow = it.hasVisualOverflow
                }
            }
        )

        if (hasOverflow && !expanded) {

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.background)

            ) {

                Text(
                    text = "... ",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier.clickable { expanded = true },
                    text = stringResource(id = R.string.view_all),
                    color = colorResource(id = R.color.cyan_primary),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}