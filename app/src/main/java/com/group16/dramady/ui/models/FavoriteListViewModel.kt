package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.DramadyRoomDatabase
import com.group16.dramady.storage.entity.FavoritedMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListViewModel : ViewModel(), MoviesListI<FavoritedMovie> {


    private val _list = MutableLiveData<List<FavoritedMovie>>().also {
        update()
    }
    override val list: LiveData<List<FavoritedMovie>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var favouritesList = DramadyRoomDatabase.getFavouritedMoviesDao().getFavouritedMovies()

            withContext(Dispatchers.Main) {
                _list.value = favouritesList
            }
        }
    }
}