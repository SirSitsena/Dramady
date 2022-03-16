package com.group16.dramady.ui.watch_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.PopularNowMovies
import com.group16.dramady.storage.entity.WatchListMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "My Watchlist"
    }
    val text: LiveData<String> = _text

    private val _list = MutableLiveData<List<WatchListMovies>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            var watchListList = MovieRoomDatabase.getWatchlistedMoviesDao()?.getWatchlistMovies()

            withContext(Dispatchers.Main) {
                it.value = watchListList
            }
        }
    }
    val list: LiveData<List<WatchListMovies>> = _list
}