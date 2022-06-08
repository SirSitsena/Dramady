package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.DramadyRoomDatabase
import com.group16.dramady.storage.entity.AllTimeBestMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTimeBestViewModel : ViewModel(), MoviesListI<AllTimeBestMovie> {

    private val _list = MutableLiveData<List<AllTimeBestMovie>>().also {

        update()
    }
    override var list: LiveData<List<AllTimeBestMovie>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            val allTimeBestList = DramadyRoomDatabase.getAllTimeBestDao().getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
                _list.value = allTimeBestList
            }
        }
    }

}