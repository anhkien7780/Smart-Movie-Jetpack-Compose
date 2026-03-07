package com.kiennv31.smartmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kiennv31.smartmovie.core.ui.theme.SmartMovieTheme
import com.kiennv31.smartmovie.presentation.screen.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartMovieTheme {
                MainScreen()
            }
        }
    }
}

