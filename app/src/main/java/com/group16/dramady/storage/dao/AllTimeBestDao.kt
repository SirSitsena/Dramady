package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.AllTimeBestMovies
import com.group16.dramady.storage.entity.PopularNowMovies

@Dao
interface AllTimeBestDao {

    @Query("SELECT * FROM all_time_best_table ORDER BY rank ASC")
    fun getMoviesSortedByRank(): List<AllTimeBestMovies>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: AllTimeBestMovies)

    @Query("DELETE FROM all_time_best_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM all_time_best_table")
    fun count(): Int
}