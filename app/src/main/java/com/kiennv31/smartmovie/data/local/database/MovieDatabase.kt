package com.kiennv31.smartmovie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kiennv31.smartmovie.data.local.dao.FavoriteMovieDao
import com.kiennv31.smartmovie.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favoriteMovieDao: FavoriteMovieDao
}
