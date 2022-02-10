package com.group16.dramady.ui.favorite_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Favorite list Fragment"
    }
    val text: LiveData<String> = _text
}