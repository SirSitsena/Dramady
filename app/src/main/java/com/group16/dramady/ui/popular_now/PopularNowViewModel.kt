package com.group16.dramady.ui.popular_now

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.PopularNowMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularNowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
    }
    val text: LiveData<String> = _text

    private val _list = MutableLiveData<List<PopularNowMovies>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            var popularNowList = MovieRoomDatabase.getPopularNowDao()?.getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
                it.value = popularNowList
            }
        }
    }
    val list: LiveData<List<PopularNowMovies>> = _list
}

