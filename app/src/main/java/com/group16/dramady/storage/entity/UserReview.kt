package com.group16.dramady.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
class UserReview(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val movieId: String,
    val review: String
)