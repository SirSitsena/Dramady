package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.AllTimeBestMovies
import com.group16.dramady.storage.entity.FavoritedMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListViewModel : ViewModel(), MoviesListI<FavoritedMovies>  {



    private val _list = MutableLiveData<List<FavoritedMovies>>().also {
        update()
    }
    override val list: LiveData<List<FavoritedMovies>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var favouritesList = MovieRoomDatabase.getFavouritedMoviesDao()?.getFavouritedMovies()

            withContext(Dispatchers.Main) {
                _list.value = favouritesList
            }
        }
    }
}