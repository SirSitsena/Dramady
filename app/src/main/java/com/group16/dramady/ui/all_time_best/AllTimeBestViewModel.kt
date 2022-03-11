package com.group16.dramady.ui.all_time_best

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.rest.apiManager
import com.group16.dramady.storage.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTimeBestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
        it.value = "Loading...."


        viewModelScope.launch(Dispatchers.IO) {

            var allTimeBestList = MovieRoomDatabase.getAllTimeBestDao()?.getMoviesSortedByRank()

            withContext(Dispatchers.Main) {
                it.value = allTimeBestList?.joinToString(separator = "\n") { movie -> movie.title }?: "Error"
            }
        }
    }
    val text: LiveData<String> = _text
}