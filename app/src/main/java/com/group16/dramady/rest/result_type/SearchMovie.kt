package com.group16.dramady.rest.result_type


data class SearchMovie (
    val searchType : String,
    val expression : String,
    val results : List<aMovie>,
    val errorMessage : String
){
    data class aMovie (
        val id: String,
        val resultType: String,
        val image: String,
        val title: String,
        val description: String
    )
}