package com.group16.dramady.ui.movie_page

import java.io.Serializable

class MovieParseable(private val rank: Int?,
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
                     private val metacriticRating: Int?): Serializable {
    public fun getRank() : Int {
        return rank ?: 0
    }
    public fun getTitle() : String {
        return title ?: "No title available.."
    }
    public fun getFullTitle() : String {
        return fullTitle ?: "No title available.."
    }
    public fun getYear() : String {
        return year?: "No year available.."
    }
    public fun getImage() : String {
        return image?: ""
    }
    public fun getReleaseDate(): String {
        return releaseDate?: "No title available.."
    }
    public fun getRuntimeMins(): Int {
        return runtimeMins ?: 0
    }
    public fun getRuntimeStr(): String {
        return runtimeStr?: "No runtime available.."
    }
    public fun getPlot(): String {
        return plot?: "No plot available.."
    }
    public fun getDirectors(): String {
        return directors?: "No directors available.."
    }
    public fun getWriters(): String {
        return writers?: "No writers available.."
    }
    public fun getStars(): String {
        return stars?: "No stars available.."
    }
    public fun getGenres(): String {
        return genres?: "No genres available.."
    }
    public fun getCompanies(): String{
        return companies?: "No companies available.."
    }
    public fun getContentRating(): String {
        return contentRating?: "No content rating available.."
    }
    public fun getImDbRating(): String {
        return imDbRating ?: "No imdb rating available.."
    }
    public fun getImDbRatingVotes(): String {
        return imDbRatingVotes?: "0"
    }
    public fun getMetacriticRating(): Int {
        return metacriticRating?: 0
    }
}