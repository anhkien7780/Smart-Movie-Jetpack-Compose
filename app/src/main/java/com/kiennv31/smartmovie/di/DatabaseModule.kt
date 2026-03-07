package com.kiennv31.smartmovie.di

import android.app.Application
import androidx.room.Room
import com.kiennv31.smartmovie.data.local.dao.FavoriteMovieDao
import com.kiennv31.smartmovie.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(db: MovieDatabase): FavoriteMovieDao {
        return db.favoriteMovieDao
    }
}
