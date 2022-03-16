package com.group16.dramady.ui.favorite_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.FavouritedMovies
import com.group16.dramady.storage.entity.WatchListMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "My Favourite list"
    }
    val text: LiveData<String> = _text

    private val _list = MutableLiveData<List<FavouritedMovies>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            var favouritesList = MovieRoomDatabase.getFavouritedMoviesDao()?.getFavouritedMovies()

            withContext(Dispatchers.Main) {
                it.value = favouritesList
            }
        }
    }
    val list: LiveData<List<FavouritedMovies>> = _list
}