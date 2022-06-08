package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.AllTimeBestMovie
import com.group16.dramady.storage.entity.PopularNowMovie

@Dao
interface PopularNowDao {

    @Query("SELECT * FROM popular_now_table ORDER BY rank ASC")
    fun getMoviesSortedByRank(): List<PopularNowMovie>

    @Query("SELECT * FROM popular_now_table WHERE id=:movieId")
    fun getMovieById(movieId: Int): PopularNowMovie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: PopularNowMovie)

    @Query("DELETE FROM popular_now_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM popular_now_table")
    fun count(): Int
}