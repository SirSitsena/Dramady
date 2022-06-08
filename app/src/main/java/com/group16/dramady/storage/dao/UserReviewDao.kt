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

    @Query("SELECT * FROM review_table WHERE id=:reviewId")
    fun getReviewById(reviewId: Int): UserReview

    @Query("SELECT * FROM review_table WHERE movieId=:titleId")
    fun getReviewByTitleId(titleId: String): UserReview?

    @Query("UPDATE review_table SET review = :review WHERE id = :reviewId")
    fun updateReview(review: String, reviewId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: UserReview)

    @Query("DELETE FROM review_table")
    suspend fun deleteAll()
}