package com.group16.dramady.ui.popular_now

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.MainActivity
import com.group16.dramady.rest.ImdbManager
import com.group16.dramady.rest.result_type.MoviesList
import com.group16.dramady.storage.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularNowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
        it.value = "Loading...."
        viewModelScope.launch(Dispatchers.IO) { //Anvand IO som Peter säger?

            var popularNowList = MovieRoomDatabase.getMovieDao()?.getMoviesSortedByRank()

//            if(popularNowList?.isEmpty() == true){
//                popularNowList = null
//            }
            withContext(Dispatchers.Main) {
//                Log.i("Name", popularNowList.toString())
                it.value = popularNowList?.joinToString(separator = "\n") { movie -> movie.title }?: "Error"
            }
        }
    }
    val text: LiveData<String> = _text
}

