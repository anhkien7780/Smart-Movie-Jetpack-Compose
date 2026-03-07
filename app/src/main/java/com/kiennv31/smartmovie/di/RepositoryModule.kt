package com.kiennv31.smartmovie.di

import com.kiennv31.smartmovie.data.repository.FavoriteMovieRepositoryImpl
import com.kiennv31.smartmovie.data.repository.MovieRepositoryImpl
import com.kiennv31.smartmovie.domain.repository.FavoriteMovieRepository
import com.kiennv31.smartmovie.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteMovieRepository(
        favoriteMovieRepositoryImpl: FavoriteMovieRepositoryImpl
    ): FavoriteMovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}
