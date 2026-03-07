package com.kiennv31.smartmovie.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kiennv31.smartmovie.presentation.screen.detail.MovieDetailScreen
import com.kiennv31.smartmovie.presentation.screen.genre_movie.GenreMoviesScreen
import com.kiennv31.smartmovie.presentation.screen.genres.GenresScreen
import com.kiennv31.smartmovie.presentation.screen.home.HomeScreen
import com.kiennv31.smartmovie.presentation.screen.search.SearchScreen
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(onMovieClick = { movieId ->
                navController.navigate(MovieDetail(movieId = movieId))
            })
        }
        composable<MovieDetail> {
            MovieDetailScreen(
                onBackClick = { navController.popBackStack() },
                onMovieClick = { movieId ->
                    navController.navigate(MovieDetail(movieId = movieId))
                })
        }
        composable<Search> {
            SearchScreen(onMovieClick = { movieId ->
                navController.navigate(MovieDetail(movieId = movieId))
            })
        }
        composable<Genres> {
            GenresScreen(onGenreClick = { genreId, genreName ->
                navController.navigate(GenreDetail(genreId = genreId, genreName = genreName))
            })
        }
        composable<GenreDetail> { backStackEntry ->
            val route: GenreDetail = backStackEntry.toRoute()
            GenreMoviesScreen(
                genreName = route.genreName,
                onBackClick = { navController.popBackStack() },
                onMovieClick = { movieId ->
                    navController.navigate(MovieDetail(movieId = movieId))
                }
            )
        }
        composable<Artists> {
            Box {}
        }
    }
}

@Serializable
object Home

@Serializable
data class MovieDetail(val movieId: Int)

@Serializable
object Search

@Serializable
object Genres

@Serializable
data class GenreDetail(val genreId: Int, val genreName: String)

@Serializable
object Artists
