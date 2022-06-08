package com.group16.dramady.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.group16.dramady.storage.dao.*
import com.group16.dramady.storage.entity.*
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(
        PopularNowMovie::class,
        UserReview::class,
        AllTimeBestMovie::class,
        FavoritedMovie::class,
        WatchListMovie::class
    ), version = 1, exportSchema = false
)
abstract class DramadyRoomDatabase : RoomDatabase() {

    abstract fun popularNowDao(): PopularNowDao
    abstract fun allTimeBestDao(): AllTimeBestDao
    abstract fun reviewDao(): UserReviewDao
    abstract fun watchListDao(): WatchListMoviesDao
    abstract fun favoritedDao(): FavoritedMoviesDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private lateinit var instance: DramadyRoomDatabase

        fun init(context: Context, scope: CoroutineScope) {
            synchronized(this) {
                this.instance = Room.databaseBuilder(
                    context.applicationContext,
                    DramadyRoomDatabase::class.java,
                    "movie_database"
                ).build()
            }
        }

        fun getPopularNowDao(): PopularNowDao {
            return instance.popularNowDao()
        }

        fun getAllTimeBestDao(): AllTimeBestDao {
            return instance.allTimeBestDao()
        }

        fun getUserReviewDao(): UserReviewDao {
            return instance.reviewDao()
        }

        fun getFavouritedMoviesDao(): FavoritedMoviesDao {
            return instance.favoritedDao()
        }

        fun getWatchlistedMoviesDao(): WatchListMoviesDao {
            return instance.watchListDao()
        }


    }
}
