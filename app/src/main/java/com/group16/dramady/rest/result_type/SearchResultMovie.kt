package com.group16.dramady.rest.result_type

data class SearchResultMovie(
    val id: String,
    val title: String,
    val originalTitle: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val image: String,
    val releaseDate: String,
    val runtimeMins: Int,
    val runtimeStr: String,
    val plot: String,
    val plotLocal: String,
    val plotLocalRtl: Boolean,
    val awards: String,
    val directors: String,
    val directorList: List<director>,
    val writers: String,
    val writerList: List<writer>,
    val stars: String,
    val starList: List<star>,
    val actorList: List<actor>,
    val genres: String,
    val genreList: List<genre>,
    val companies: String,
    val contentRating: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val metaCriticRating: Int
) {
    data class director(
     val id: String,
     val name: String
 )
    data class writer(
        val id: String,
        val name: String
    )
    data class star(
        val id: String,
        val image: String,
        val name: String,
        val asCharacter: String
    )
    data class actor(
        val id: String,
        val image: String,
        val name: String,
        val asCharacter: String
    )
    data class genre(
        val key: String,
        val value: String
    )
}