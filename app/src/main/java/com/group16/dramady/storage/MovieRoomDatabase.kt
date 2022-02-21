package com.group16.dramady.storage

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.group16.dramady.rest.ImdbManager
import com.group16.dramady.storage.dao.MovieDao
import com.group16.dramady.storage.dao.UserReviewDao
import com.group16.dramady.storage.entity.Movie
import com.group16.dramady.storage.entity.UserReview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = arrayOf(Movie::class, UserReview::class), version = 1, exportSchema = false)
public abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun reviewDao(): UserReviewDao

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

        fun getMovieDao(): MovieDao? {
            return INSTANCE?.movieDao()
        }

        fun getUserReviewDao(): UserReviewDao? {
            return INSTANCE?.reviewDao()
        }
    }
}
