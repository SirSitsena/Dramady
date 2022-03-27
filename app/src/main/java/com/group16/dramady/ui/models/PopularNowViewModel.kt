package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.PopularNowMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularNowViewModel : ViewModel(), MoviesListI<PopularNowMovies> {

    private val _list = MutableLiveData<List<PopularNowMovies>>().also {
        update()
    }
    override var list: LiveData<List<PopularNowMovies>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var popularNowList = MovieRoomDatabase.getPopularNowDao()?.getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
                _list.value = popularNowList
            }
        }
    }
}

