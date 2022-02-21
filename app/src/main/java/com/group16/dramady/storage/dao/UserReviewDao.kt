package com.group16.dramady.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group16.dramady.storage.entity.UserReview

@Dao
interface UserReviewDao {

    @Query("SELECT * FROM review_table")    // ORDER BY *** ASC
    fun getAllReviews(): List<UserReview>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: UserReview)

    @Query("DELETE FROM review_table")
    suspend fun deleteAll()
}