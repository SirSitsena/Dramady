package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.AllTimeBestMovies
import com.group16.dramady.storage.entity.WatchListMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListViewModel : ViewModel(), MoviesListI<WatchListMovies>  {

    private val _list = MutableLiveData<List<WatchListMovies>>().also {
        update()
    }
    override val list: LiveData<List<WatchListMovies>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var watchListList = MovieRoomDatabase.getWatchlistedMoviesDao()?.getWatchlistMovies()

            withContext(Dispatchers.Main) {
                _list.value = watchListList
            }
        }
    }

}