package com.group16.dramady.ui.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.rest.apiManager
import com.group16.dramady.rest.result_type.SearchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
        it.value = "Search Page"
    }

    private var _resultText = MutableLiveData<String>()
    private var _resultList = MutableLiveData<List<SearchMovie.aMovie>>()

    var resultList: LiveData<List<SearchMovie.aMovie>> = _resultList


    val searchResult: LiveData<String> = _resultText
    val text: LiveData<String> = _text

    fun search(keywords: String){
        _resultText.value = "Loading..."

        viewModelScope.launch(Dispatchers.IO) {
            val search = apiManager.getSearchMoviesByKeywords(keywords)
            val movies = search?.results

            withContext(Dispatchers.Main) {
                //_resultText.value = moviesString
                Log.i("movies: ", movies.toString())
                if(movies != null){
                    _resultList.value = movies!!
                    _resultText.value = ""
                }
            }
        }
    }
}