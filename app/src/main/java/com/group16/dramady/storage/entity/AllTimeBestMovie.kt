package com.group16.dramady.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_time_best_table")
open class AllTimeBestMovie(
    @PrimaryKey(autoGenerate = false) val id: String,
    val rank: Int,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val releaseDate: String,
    val runtimeMins: Int,
    val runtimeStr: String,
    val plot: String,
    val directors: String,
    val writers: String,
    val stars: String,
    val genres: String,
    val companies: String,
    val contentRating: String,
    val imDbRating: Float,
    val imDbRatingVotes: Int,
    val metacriticRating: Int,
)