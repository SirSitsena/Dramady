package com.group16.dramady.ui

import com.group16.dramady.storage.entity.AllTimeBestMovies

class Mediator<T>(
    id: String,
    rank: Int,
    title: String,
    fullTitle: String,
    year: String,
    image: String,
    releaseDate: String,
    runtimeMins: Int,
    runtimeStr: String,
    plot: String,
    directors: String,
    writers: String,
    stars: String,
    genres: String,
    companies: String,
    contentRating: String,
    imDbRating: Float,
    imDbRatingVotes: Int,
    metacriticRating: Int
) : AllTimeBestMovies(
    id,
    rank,
    title,
    fullTitle,
    year,
    image,
    releaseDate,
    runtimeMins,
    runtimeStr,
    plot,
    directors,
    writers,
    stars,
    genres,
    companies,
    contentRating,
    imDbRating,
    imDbRatingVotes,
    metacriticRating
) {
}