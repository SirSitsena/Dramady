package com.group16.dramady.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.group16.dramady.storage.dao.*
import com.group16.dramady.storage.entity.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(
    PopularNowMovies::class,
    UserReview::class,
    AllTimeBestMovies::class,
    FavouritedMovies::class,
    WatchListMovies::class), version = 1, exportSchema = false)
public abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun popularNowDao(): PopularNowDao
    abstract fun allTimeBestDao(): AllTimeBestDao
    abstract fun reviewDao(): UserReviewDao
    abstract fun watchListDao(): WatchListMoviesDao
    abstract fun favouritedDao(): FavouritedMoviesDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MovieRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun getPopularNowDao(): PopularNowDao? {
            return INSTANCE?.popularNowDao()
        }
        fun getAllTimeBestDao(): AllTimeBestDao? {
            return INSTANCE?.allTimeBestDao()
        }

        fun getUserReviewDao(): UserReviewDao? {
            return INSTANCE?.reviewDao()
        }

        fun getFavouritedMoviesDao(): FavouritedMoviesDao? {
            return INSTANCE?.favouritedDao()
        }
        fun getWatchlistedMoviesDao(): WatchListMoviesDao? {
            return INSTANCE?.watchListDao()
        }



    }
}
