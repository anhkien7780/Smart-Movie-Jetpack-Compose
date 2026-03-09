package com.kiennv31.smartmovie.presentation.screen.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kiennv31.smartmovie.R

@Composable
fun SplashScreen(onFinish: @Composable () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.ic_logo_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition
    )

    LottieAnimation(composition, { progress }, modifier = Modifier)

    if (progress == 1f) {
        onFinish()
    }
}