package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.DramadyRoomDatabase
import com.group16.dramady.storage.entity.WatchListMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListViewModel : ViewModel(), MoviesListI<WatchListMovie> {

    private val _list = MutableLiveData<List<WatchListMovie>>().also {
        update()
    }
    override val list: LiveData<List<WatchListMovie>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var watchListList = DramadyRoomDatabase.getWatchlistedMoviesDao().getWatchlistMovies()

            withContext(Dispatchers.Main) {
                _list.value = watchListList
            }
        }
    }

}