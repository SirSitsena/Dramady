package com.anestis.dramady.ui.watch_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WatchListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Watch list Fragment"
    }
    val text: LiveData<String> = _text
}