package com.kiennv31.smartmovie.presentation.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kiennv31.smartmovie.R

enum class BottomNavItem(val title: String, val iconRes: Int) {
    Discovery("Discovery", R.drawable.ic_discorvery),
    Search("Search", R.drawable.ic_search),
    Genres("Genres", R.drawable.ic_genres),
    Artists("Artists", R.drawable.ic_artist)
}

@Composable
fun BaseBottomAppBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val items = BottomNavItem.entries.toTypedArray()

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
private fun BaseBottomAppBarPreview() {
    BaseBottomAppBar(
        selectedItem = 0,
        onItemClick = {}
    )
}
