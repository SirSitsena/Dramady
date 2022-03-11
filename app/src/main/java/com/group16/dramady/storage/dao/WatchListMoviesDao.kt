package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.FavouritedMovies
import com.group16.dramady.storage.entity.WatchListMovies

@Dao
interface WatchListMoviesDao {

    @Query("SELECT * FROM watchlist_movies_table")
    fun getWatchlistMovies(): List<WatchListMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WatchListMovies)

    @Query("DELETE FROM watchlist_movies_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM watchlist_movies_table")
    fun count(): Int

    @Query("SELECT * FROM watchlist_movies_table WHERE id = :movieId")
    fun isWatchlisted(movieId: String): Boolean

}