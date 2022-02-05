package com.anestis.dramady.ui.popular_now

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PopularNowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Popular now movies Fragment"
    }
    val text: LiveData<String> = _text
}