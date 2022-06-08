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
    val directorList: List<Director>,
    val writers: String,
    val writerList: List<Writer>,
    val stars: String,
    val starList: List<Star>,
    val actorList: List<Actor>,
    val genres: String,
    val genreList: List<Genre>,
    val companies: String,
    val contentRating: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val metaCriticRating: Int
) {
    data class Director(
        val id: String,
        val name: String
    )

    data class Writer(
        val id: String,
        val name: String
    )

    data class Star(
        val id: String,
        val image: String,
        val name: String,
        val asCharacter: String
    )

    data class Actor(
        val id: String,
        val image: String,
        val name: String,
        val asCharacter: String
    )

    data class Genre(
        val key: String,
        val value: String
    )
}