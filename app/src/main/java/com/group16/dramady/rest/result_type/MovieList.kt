package com.group16.dramady.rest.result_type

data class MoviesList (

    val items : List<Movie>,
    val errorMessage : String
){
    data class Movie (

        val id : String,
        val rank : String,
        val rankUpDown : String,
        val title : String,
        val fullTitle : String,
        val year : String,
        val image : String,
        val crew : String,
        val imDbRating : String,
        val imDbRatingCount : String
    )
}