package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY rank ASC")
    fun getMoviesSortedByRank(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie_table")
    fun count(): Int
}