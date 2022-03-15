package com.group16.dramady.ui.movie_page

import java.io.Serializable

class MovieParseable(private val rank: Int,
                     private val title: String,
                     private val fullTitle: String,
                     private val year: String,
                     private val image: String,
                     private val releaseDate: String,
                     private val runtimeMins: Int,
                     private val runtimeStr: String,
                     private val plot: String,
                     private val directors: String,
                     private val writers: String,
                     private val stars: String,
                     private val genres: String,
                     private val companies: String,
                     private val contentRating: String,
                     private val imDbRating: Float,
                     private val imDbRatingVotes: Int,
                     private val metacriticRating: Int): Serializable {
    public fun getRank() : Int {
        return rank
    }
    public fun getTitle() : String {
        return title
    }
    public fun getFullTitle() : String {
        return fullTitle
    }
    public fun getYear() : String {
        return year
    }
    public fun getImage() : String {
        return image
    }
    public fun getReleaseDate(): String {
        return releaseDate
    }
    public fun getRuntimeMins(): Int {
        return runtimeMins
    }
    public fun getRuntimeStr(): String {
        return runtimeStr
    }
    public fun getPlot(): String {
        return plot
    }
    public fun getDirectors(): String {
        return directors
    }
    public fun getWriters(): String {
        return writers
    }
    public fun getStars(): String {
        return stars
    }
    public fun getGenres(): String {
        return genres
    }
    public fun getCompanies(): String{
        return companies
    }
    public fun getContentRating(): String {
        return contentRating
    }
    public fun getImDbRating(): Float {
        return imDbRating
    }
    public fun getImDbRatingVotes(): Int {
        return imDbRatingVotes
    }
    public fun getMetacriticRating(): Int {
        return metacriticRating
    }
}


    /*private val rank : Int
        get() {
            return rank
        }
    private val title : String
        get() {
            return title
        }
    private val fullTitle : String
        get() {
            return fullTitle
        }
    private val year : String
        get() {
            return year
        }
    private val image : String
        get() {
            return image
        }
    private val releaseDate: String
        get() {
            return releaseDate
        }
    private val runtimeMins: Int
        get() {
            return runtimeMins
        }
    private val runtimeStr: String
        get() {
            return runtimeStr
        }
    private val plot: String
        get() {
            return plot
        }
    private val directors: String
        get() {
            return directors
        }
    private val writers: String
        get() {
            return writers
        }
    private val stars: String
        get() {
            return stars
        }
    private val genres: String
        get() {
            return genres
        }
    private val companies: String
        get() {
            return companies
        }
    private val contentRating: String
        get() {
            return contentRating
        }
    private val imDbRating: Float
        get() {
            return imDbRating
        }
    private val imDbRatingVotes: Int
        get() {
            return imDbRatingVotes
        }
    private val metacriticRating: Int
        get() {
            return metacriticRating
        } */
