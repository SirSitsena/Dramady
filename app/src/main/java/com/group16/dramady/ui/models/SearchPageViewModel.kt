package com.group16.dramady.ui.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.rest.apiManager
import com.group16.dramady.rest.result_type.FoundMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
        it.value = "Search Page"
    }

    private val statusBar = MutableLiveData<Boolean>()
    private val _resultList = MutableLiveData<List<FoundMovies.Movie>>()
    private val _resultError = MutableLiveData<ResultErrors>().also {
        it.value = ResultErrors.NONE
    }

    val text: LiveData<String> = _text
    val searchStatus: LiveData<Boolean> = statusBar
    val resultList: LiveData<List<FoundMovies.Movie>> = _resultList
    val resultError: LiveData<ResultErrors> = _resultError

    fun search(phrase: String, fail: () -> Unit) {
        statusBar.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val search = apiManager.getFoundMoviesByPhrase(phrase)
            val movies = search?.foundMovies

            withContext(Dispatchers.Main) {
                Log.i("movies: ", movies.toString())
                if (movies != null && movies.isNotEmpty()) {    //The movie list exists and is not empty
                    _resultList.value = movies!!
                    _resultError.value = ResultErrors.NONE
                } else if(movies != null) {                     //The movie list exists but is empty
                    _resultList.value = movies!!
                    _resultError.value = ResultErrors.EMPTY_RESULTS
                } else {                                        //The movie list do not exist
                    _resultError.value = ResultErrors.NO_INTERNET
                    fail()
                }
                statusBar.value = false
            }
        }
    }
}

enum class ResultErrors{
    NO_INTERNET, EMPTY_RESULTS, NONE;
}