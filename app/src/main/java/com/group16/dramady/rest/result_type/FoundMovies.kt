package com.group16.dramady.rest.result_type

import com.google.gson.annotations.SerializedName


data class FoundMovies(
    val searchType: String,
    val expression: String,
    @SerializedName("results") val foundMovies: List<Movie>,
    val errorMessage: String
) {
    data class Movie(
        val id: String,
        val resultType: String,
        val image: String,
        val title: String,
        val description: String
    )
}