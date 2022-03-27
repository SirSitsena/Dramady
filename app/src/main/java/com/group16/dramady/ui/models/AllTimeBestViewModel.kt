package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.AllTimeBestMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTimeBestViewModel : ViewModel(), MoviesListI<AllTimeBestMovies> {

    private val _list = MutableLiveData<List<AllTimeBestMovies>>().also {

        update()
    }
    override var list: LiveData<List<AllTimeBestMovies>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            var allTimeBestList = MovieRoomDatabase.getAllTimeBestDao()?.getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
               _list.value = allTimeBestList
            }
        }
    }

}