package com.kiennv31.smartmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kiennv31.smartmovie.core.ui.theme.SmartMovieTheme
import com.kiennv31.smartmovie.presentation.screen.main.MainScreen
import com.kiennv31.smartmovie.presentation.screen.splash_screen.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartMovieTheme {
                SplashScreen {
                    MainScreen()
                }
            }
        }
    }
}

