package com.group16.dramady.ui.movie_page

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.rest.apiManager
import com.group16.dramady.rest.result_type.Reviews
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.FavouritedMovies
import com.group16.dramady.storage.entity.UserReview
import com.group16.dramady.storage.entity.WatchListMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePageViewModel(titleId: String?) : ViewModel() {
    private var titleId = titleId

    private var _myReview = MutableLiveData<UserReview>().also {
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO){
                val review = MovieRoomDatabase.getUserReviewDao()?.getReviewByTitleId(titleId)
                Log.i("review: ", review.toString())
                if(review != null){
                    withContext(Dispatchers.Main){
                        it.value = review
                    }
                }
            }
        }
    }

    private var _reviews = MutableLiveData<List<Reviews>>().also {
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO) {
                val list = apiManager.getReviewsByTitleId(titleId)
                Log.i("review list:", list.toString())
                withContext(Dispatchers.Main){
                    it.value = list
                }
            }
        }
    }

    private var _isFavourited = MutableLiveData<Boolean>().also {
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO) {
                val isMovieFavourited = MovieRoomDatabase.getFavouritedMoviesDao()!!.isFavourited(titleId)
                withContext(Dispatchers.Main) {
                    it.value = isMovieFavourited
                }
            }
        } else {
            it.value = false
        }
    }

    private var _isWatchlisted = MutableLiveData<Boolean>().also {
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO) {
                val isMovieWatchlisted = MovieRoomDatabase.getWatchlistedMoviesDao()?.isWatchlisted(titleId)
                withContext(Dispatchers.Main) {
                    it.value = isMovieWatchlisted
                }
            }
        } else {
            it.value = false
        }
    }

    fun deleteFavourite(titleId: String?){
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO){
                MovieRoomDatabase.getFavouritedMoviesDao()?.deleteById(titleId)
                refresh()
            }
        }
    }

    fun addFavourite(titleId: String?, movie: MovieParseable){
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO){
                // Remove from favourites
                MovieRoomDatabase.getFavouritedMoviesDao()?.insert(
                    FavouritedMovies(titleId, movie.getRank(), movie.getTitle(), movie.getFullTitle(), movie.getYear(), movie.getImage(), movie.getReleaseDate(), movie.getRuntimeMins(), movie.getRuntimeStr(), movie.getPlot(),
                    movie.getDirectors(), movie.getWriters(), movie.getStars(), movie.getGenres(), movie.getCompanies(), movie.getContentRating(), movie.getImDbRating().toFloat(), movie.getImDbRatingVotes().toInt(), movie.getMetacriticRating())
                )
                // Refresh the livedata to reflect the icon on screen.
                refresh()
            }
        }
    }

    fun deleteWatchlistItem(titleId: String?){
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO){
                MovieRoomDatabase.getWatchlistedMoviesDao()?.deleteById(titleId)
                refresh()
            }
        }
    }

    fun addWatchlistItem(titleId: String?, movie: MovieParseable){
        if(titleId != null){
            viewModelScope.launch(Dispatchers.IO){
                MovieRoomDatabase.getWatchlistedMoviesDao()?.insert(
                    WatchListMovies(titleId, movie.getRank(), movie.getTitle(), movie.getFullTitle(), movie.getYear(), movie.getImage(), movie.getReleaseDate(), movie.getRuntimeMins(), movie.getRuntimeStr(), movie.getPlot(),
                    movie.getDirectors(), movie.getWriters(), movie.getStars(), movie.getGenres(), movie.getCompanies(), movie.getContentRating(), movie.getImDbRating().toFloat(), movie.getImDbRatingVotes().toInt(), movie.getMetacriticRating())
                )
                refresh()
            }
        }
    }

    fun refresh() {
        if(titleId != null){
            val tId = titleId
            viewModelScope.launch(Dispatchers.IO) {
                val isMovieFavourited = MovieRoomDatabase.getFavouritedMoviesDao()?.isFavourited(tId!!)
                val isMovieWatchlisted = MovieRoomDatabase.getWatchlistedMoviesDao()?.isWatchlisted(tId!!)
                withContext(Dispatchers.Main) {
                    _isFavourited.value = isMovieFavourited
                    _isWatchlisted.value = isMovieWatchlisted
                }
            }
        } else {
            _isFavourited.value = false
            _isWatchlisted.value = false
        }
    }

    val isFavourited: LiveData<Boolean> = _isFavourited
    val isWatchlisted: LiveData<Boolean> = _isWatchlisted
    val reviews: LiveData<List<Reviews>> = _reviews
    val myReview: LiveData<UserReview> = _myReview
}