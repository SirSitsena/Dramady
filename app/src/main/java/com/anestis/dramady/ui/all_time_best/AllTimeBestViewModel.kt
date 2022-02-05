package com.anestis.dramady.ui.all_time_best

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllTimeBestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is All time best movies Fragment"
    }
    val text: LiveData<String> = _text
}