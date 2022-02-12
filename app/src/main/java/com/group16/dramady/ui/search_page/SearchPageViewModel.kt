package com.group16.dramady.ui.search_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group16.dramady.rest.ImdbManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().also {
        it.value = "Search Page"

    }
    val text: LiveData<String> = _text
}