package com.group16.dramady.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class Movie (
    @PrimaryKey(autoGenerate = false) val id: String,
    val title : String,
    val fullTitle : String,
    val description: String,
    val image : String,
    val year : String,
    val crew : String,
    val imDbRating : String,
    val rank : String,
    val rankUpDown : String,
    val imDbRatingCount : String,
    val userReview: Int
)