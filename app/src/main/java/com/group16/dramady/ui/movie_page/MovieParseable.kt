package com.group16.dramady.ui.movie_page

import java.io.Serializable

class MovieParseable(
    private val rank: Int?,
    private val title: String?,
    private val fullTitle: String?,
    private val year: String?,
    private val image: String?,
    private val releaseDate: String?,
    private val runtimeMins: Int?,
    private val runtimeStr: String?,
    private val plot: String?,
    private val directors: String?,
    private val writers: String?,
    private val stars: String?,
    private val genres: String?,
    private val companies: String?,
    private val contentRating: String?,
    private val imDbRating: String?,
    private val imDbRatingVotes: String?,
    private val metacriticRating: Int?
) : Serializable {

    companion object {
        const val KEY_ID = "id"
        const val KEY_MOVIE = "movie"
    }

    fun getRank(): Int {
        return rank ?: 0
    }

    fun getTitle(): String {
        return title ?: "No title available.."
    }

    fun getFullTitle(): String {
        return fullTitle ?: "No title available.."
    }

    fun getYear(): String {
        return year ?: "No year available.."
    }

    fun getImage(): String {
        return image ?: ""
    }

    fun getReleaseDate(): String {
        return releaseDate ?: "No title available.."
    }

    fun getRuntimeMins(): Int {
        return runtimeMins ?: 0
    }

    fun getRuntimeStr(): String {
        return runtimeStr ?: "No runtime available.."
    }

    fun getPlot(): String {
        return plot ?: "No plot available.."
    }

    fun getDirectors(): String {
        return directors ?: "No directors available.."
    }

    fun getWriters(): String {
        return writers ?: "No writers available.."
    }

    fun getStars(): String {
        return stars ?: "No stars available.."
    }

    fun getGenres(): String {
        return genres ?: "No genres available.."
    }

    fun getCompanies(): String {
        return companies ?: "No companies available.."
    }

    fun getContentRating(): String {
        return contentRating ?: "No content rating available.."
    }

    fun getImDbRating(): String {
        return imDbRating ?: "No imdb rating available.."
    }

    fun getImDbRatingVotes(): String {
        return imDbRatingVotes ?: "0"
    }

    fun getMetacriticRating(): Int {
        return metacriticRating ?: 0
    }

}