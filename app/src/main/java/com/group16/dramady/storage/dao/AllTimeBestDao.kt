package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.AllTimeBestMovie
import com.group16.dramady.storage.entity.UserReview

@Dao
interface AllTimeBestDao {

    @Query("SELECT * FROM all_time_best_table ORDER BY rank ASC")
    fun getMoviesSortedByRank(): List<AllTimeBestMovie>

    @Query("SELECT * FROM all_time_best_table WHERE id=:movieId")
    fun getMovieById(movieId: Int): AllTimeBestMovie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: AllTimeBestMovie)

    @Query("DELETE FROM all_time_best_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM all_time_best_table")
    fun count(): Int
}