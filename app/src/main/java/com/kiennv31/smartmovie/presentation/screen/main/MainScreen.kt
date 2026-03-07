package com.kiennv31.smartmovie.presentation.screen.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import com.kiennv31.smartmovie.presentation.common.BaseBottomAppBar
import com.kiennv31.smartmovie.presentation.navigation.Artists
import com.kiennv31.smartmovie.presentation.navigation.Home
import com.kiennv31.smartmovie.presentation.navigation.Genres
import com.kiennv31.smartmovie.presentation.navigation.MyNavHost
import com.kiennv31.smartmovie.presentation.navigation.Search

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },
        bottomBar = {
            BaseBottomAppBar(
                selectedItem = selectedTab,
                onItemClick = { index ->
                    selectedTab = index
                    when (index) {
                        0 -> navController.navigate(Home) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }

                        1 -> navController.navigate(Search) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }

                        2 -> navController.navigate(Genres) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }

                        3 -> navController.navigate(Artists) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        MyNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}
