package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.FavouritedMovies

@Dao
interface FavouritedMoviesDao {

    @Query("SELECT * FROM favourited_movies_table")
    fun getFavouritedMovies(): List<FavouritedMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavouritedMovies)

    @Query("DELETE FROM favourited_movies_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM favourited_movies_table")
    fun count(): Int

    @Query("SELECT * FROM favourited_movies_table WHERE id = :movieId")
    fun isFavourited(movieId: String): Boolean
}