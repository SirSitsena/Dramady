package com.group16.dramady.rest.result_type

data class Review(
    val id: Int,
    val content: String,
    val titleId: String,
    val userId: Int
)