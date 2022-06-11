package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.storage.DramadyRoomDatabase
import com.group16.dramady.storage.entity.PopularNowMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularNowViewModel : ViewModel(), MoviesListI<PopularNowMovie> {

    private val _list = MutableLiveData<List<PopularNowMovie>>().also {
        update()
    }
    override var list: LiveData<List<PopularNowMovie>> = _list
    override fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            val popularNowList = DramadyRoomDatabase.getPopularNowDao().getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
                _list.value = popularNowList
            }
        }
    }
}

