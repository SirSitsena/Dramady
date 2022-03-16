package com.group16.dramady.ui.movie_page

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePageViewModel(titleId: String?) : ViewModel() {
    private var titleId = titleId
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

    public fun refresh() {
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
}